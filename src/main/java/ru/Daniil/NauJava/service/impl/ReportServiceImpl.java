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
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

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

    public ReportServiceImpl(ReportRepository reportRepository,
                             UserRepository userRepository,
                             BankAccountRepository bankAccountRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public Long createReport() {
        Report report = new Report(ReportStatus.CREATED, """
                Отчет еще формируется.
                Статус отчета: CREATED
                """);
        Report savedReport = reportRepository.save(report);
        CompletableFuture.runAsync(() -> generateReport(savedReport.getId()));
        return savedReport.getId();
    }

    @Override
    public Report getReport(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Отчет с id=" + reportId + " не найден"));
    }

    private void generateReport(Long reportId) {
        Report report = getReport(reportId);
        long totalStartTime = System.currentTimeMillis();

        AtomicLong userCount = new AtomicLong();
        AtomicLong userCountElapsed = new AtomicLong();
        AtomicLong bankAccountsElapsed = new AtomicLong();
        AtomicReference<List<BankAccount>> bankAccountsRef = new AtomicReference<>(List.of());

        try {
            Thread usersThread = new Thread(() -> {
                long startTime = System.currentTimeMillis();
                userCount.set(userRepository.count());
                userCountElapsed.set(System.currentTimeMillis() - startTime);
            });

            Thread bankAccountsThread = new Thread(() -> {
                long startTime = System.currentTimeMillis();
                bankAccountsRef.set(new ArrayList<>(bankAccountRepository.findAllWithUser()));
                bankAccountsElapsed.set(System.currentTimeMillis() - startTime);
            });

            usersThread.start();
            bankAccountsThread.start();

            usersThread.join();
            bankAccountsThread.join();

            long totalElapsed = System.currentTimeMillis() - totalStartTime;

            report.setContent(buildReportContent(
                    userCount.get(),
                    userCountElapsed.get(),
                    bankAccountsRef.get(),
                    bankAccountsElapsed.get(),
                    totalElapsed
            ));
            report.setStatus(ReportStatus.COMPLETED);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            report.setStatus(ReportStatus.ERROR);
            report.setContent(buildErrorContent("Формирование отчета было прервано"));
        } catch (Exception e) {
            report.setStatus(ReportStatus.ERROR);
            report.setContent(buildErrorContent("Ошибка при формировании отчета: " + e.getMessage()));
        }

        reportRepository.save(report);
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
}
