package ru.Daniil.NauJava.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.Daniil.NauJava.model.BankAccount;
import ru.Daniil.NauJava.model.BankCard;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.BankAccountRepository;
import ru.Daniil.NauJava.repo.BankCardRepository;
import ru.Daniil.NauJava.repo.UserRepository;
import ru.Daniil.NauJava.service.AccountService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Реализация сервиса аккаунтов
 *
 * @author Daniil Mezev
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankCardRepository bankCardRepository;
    private final PlatformTransactionManager transactionManager;

    public AccountServiceImpl(UserRepository userRepository,
                              BankAccountRepository bankAccountRepository,
                              BankCardRepository bankCardRepository,
                              PlatformTransactionManager transactionManager) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankCardRepository = bankCardRepository;
        this.transactionManager = transactionManager;
    }

    /**
     * Транзакционно создать аккаунт с картой
     */
    @Override
    public void createAccountWithCard(Long userId,
                                      String accountNumber,
                                      BigDecimal balance,
                                      String accountStatus,
                                      String cardNumber,
                                      LocalDate expirationDate,
                                      String paymentSystem) {

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        definition.setTimeout(30);
        definition.setReadOnly(false);

        TransactionStatus status = transactionManager.getTransaction(definition);


        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

            BankAccount account = new BankAccount();
            account.setAccountNumber(accountNumber);
            account.setBalance(balance);
            account.setStatus(accountStatus);
            account.setUser(user);

            BankAccount savedAccount = bankAccountRepository.save(account);

            if (cardNumber == null || cardNumber.isBlank()) {
                throw new IllegalArgumentException("Номер карты не должен быть пустым");
            }

            BankCard card = new BankCard();
            card.setCardNumber(cardNumber);
            card.setExpirationDate(expirationDate);
            card.setPaymentSystem(paymentSystem);
            card.setBankAccount(savedAccount);

            bankCardRepository.save(card);

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
    }
}
