package ru.Daniil.NauJava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Daniil.NauJava.model.BankAccount;
import ru.Daniil.NauJava.model.BankCard;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.BankAccountRepository;
import ru.Daniil.NauJava.repo.BankCardRepository;
import ru.Daniil.NauJava.repo.UserRepository;
import ru.Daniil.NauJava.service.AccountService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * Тесты с проверкой транзации для сервиса аккаунтов
 *
 * @author Daniil Mezev
 */
@SpringBootTest
class AccountServiceTransactionTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankCardRepository bankCardRepository;

    @AfterEach
    void cleanUp() {
        bankCardRepository.deleteAll();
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testCreateAccountWithCardSuccess() {
        User user = userRepository.save(new User(
                "Daniil",
                "daniil-" + UUID.randomUUID() + "@mail.com",
                "+79990000007"
        ));

        String accountNumber = "ACC-" + UUID.randomUUID();
        String cardNumber = "1234567890123456";

        accountService.createAccountWithCard(
                user.getId(),
                accountNumber,
                new BigDecimal("1500.00"),
                "ACTIVE",
                cardNumber,
                LocalDate.now().plusYears(2),
                "VISA"
        );

        Optional<BankAccount> savedAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        Optional<BankCard> savedCard = bankCardRepository.findByCardNumber(cardNumber);

        Assertions.assertTrue(savedAccount.isPresent());
        Assertions.assertTrue(savedCard.isPresent());
        Assertions.assertEquals(accountNumber, savedAccount.get().getAccountNumber());
        Assertions.assertEquals(cardNumber, savedCard.get().getCardNumber());
    }

    @Test
    void testCreateAccountWithCardRollback() {
        User user = userRepository.save(new User(
                "Rollback User",
                "rollback-" + UUID.randomUUID() + "@mail.com",
                "+79990000008"
        ));

        String accountNumber = "ACC-" + UUID.randomUUID();

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                accountService.createAccountWithCard(
                        user.getId(),
                        accountNumber,
                        new BigDecimal("2500.00"),
                        "ACTIVE",
                        "",
                        LocalDate.now().plusYears(2),
                        "VISA"
                )
        );

        Optional<BankAccount> savedAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        long cardsCount = StreamSupport.stream(bankCardRepository.findAll().spliterator(), false).count();

        Assertions.assertTrue(savedAccount.isEmpty());
        Assertions.assertEquals(0, cardsCount);
    }
}