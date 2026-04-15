package ru.Daniil.NauJava.service.impl;

import org.springframework.stereotype.Service;
import ru.Daniil.NauJava.model.BankAccount;
import ru.Daniil.NauJava.model.Report;
import ru.Daniil.NauJava.model.ReportStatus;
import ru.Daniil.NauJava.repo.BankAccountRepository;
import ru.Daniil.NauJava.repo.ReportRepository;
import ru.Daniil.NauJava.repo.UserRepository;
import ru.Daniil.NauJava.service.ReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * Реализация сервиса отчетов.
 *
 * @author Daniil Mezev
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ExecutorService reportExecutorService;

    public ReportServiceImpl(ReportRepository reportRepository,
                             UserRepository userRepository,
                             BankAccountRepository bankAccountRepository,
                             ExecutorService reportExecutorService) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.reportExecutorService = reportExecutorService;
    }

    @Override
    public Long createReport() {
        Report report = new Report(ReportStatus.CREATED, """
                Отчет еще формируется.
                Статус отчета: CREATED
                """);
        Report savedReport = reportRepository.save(report);
        generateReport(savedReport.getId());
        return savedReport.getId();
    }

    @Override
    public Report getReport(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Отчет с id=" + reportId + " не найден"));
    }

    private void generateReport(Long reportId) {
        long totalStartTime = System.currentTimeMillis();

        CompletableFuture<TimedResult<Long>> userCountFuture = CompletableFuture.supplyAsync(
                () -> measure(userRepository::count),
                reportExecutorService
        );
        CompletableFuture<TimedResult<List<BankAccount>>> bankAccountsFuture = CompletableFuture.supplyAsync(
                () -> measure(() -> new ArrayList<>(bankAccountRepository.findAllWithUser())),
                reportExecutorService
        );

        userCountFuture
                .thenCombine(bankAccountsFuture, (userCount, bankAccounts) -> {
                    Report report = getReport(reportId);
                    report.setContent(buildReportContent(
                            userCount.value(),
                            userCount.elapsedMillis(),
                            bankAccounts.value(),
                            bankAccounts.elapsedMillis(),
                            System.currentTimeMillis() - totalStartTime
                    ));
                    report.setStatus(ReportStatus.COMPLETED);
                    return report;
                })
                .exceptionally(exception -> {
                    Report report = getReport(reportId);
                    report.setStatus(ReportStatus.ERROR);
                    report.setContent(buildErrorContent("Ошибка при формировании отчета: "
                            + getExceptionMessage(exception)));
                    return report;
                })
                .thenAccept(reportRepository::save);
    }

    private <T> TimedResult<T> measure(Supplier<T> supplier) {
        long startTime = System.currentTimeMillis();
        T value = supplier.get();
        return new TimedResult<>(value, System.currentTimeMillis() - startTime);
    }

    private String getExceptionMessage(Throwable exception) {
        Throwable cause = exception instanceof CompletionException && exception.getCause() != null
                ? exception.getCause()
                : exception;
        return cause.getMessage();
    }

    private String buildReportContent(long userCount,
                                      long userCountElapsed,
                                      List<BankAccount> bankAccounts,
                                      long bankAccountsElapsed,
                                      long totalElapsed) {
        StringBuilder content = new StringBuilder();
        content.append("Количество зарегистрированных пользователей: ")
                .append(userCount)
                .append("\n");
        content.append("Время вычисления количества пользователей, мс: ")
                .append(userCountElapsed)
                .append("\n");
        content.append("Время получения списка банковских счетов, мс: ")
                .append(bankAccountsElapsed)
                .append("\n");
        content.append("Общее время формирования отчета, мс: ")
                .append(totalElapsed)
                .append("\n\n");
        content.append("Список банковских счетов:\n");

        if (bankAccounts.isEmpty()) {
            content.append("Банковские счета не найдены");
            return content.toString();
        }

        for (BankAccount bankAccount : bankAccounts) {
            content.append("ID счета: ").append(bankAccount.getId()).append(", ")
                    .append("Номер счета: ").append(bankAccount.getAccountNumber()).append(", ")
                    .append("Баланс: ").append(bankAccount.getBalance()).append(", ")
                    .append("Статус: ").append(bankAccount.getStatus()).append(", ")
                    .append("ID пользователя: ").append(bankAccount.getUser().getId()).append(", ")
                    .append("ФИО пользователя: ").append(bankAccount.getUser().getFullName()).append(", ")
                    .append("Email пользователя: ").append(bankAccount.getUser().getEmail()).append("\n");
        }

        return content.toString();
    }

    private String buildErrorContent(String message) {
        return "Отчет не сформирован. Статус отчета: ERROR. " + message;
    }

    private record TimedResult<T>(T value, long elapsedMillis) {
    }
}
