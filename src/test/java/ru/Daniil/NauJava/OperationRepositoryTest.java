package ru.Daniil.NauJava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Daniil.NauJava.model.BankAccount;
import ru.Daniil.NauJava.model.Operation;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.BankAccountRepository;
import ru.Daniil.NauJava.repo.OperationRepository;
import ru.Daniil.NauJava.repo.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Тесты для репозитория операций
 *
 * @author Daniil Mezev
 */
@SpringBootTest
class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        operationRepository.deleteAll();
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testFindByTypeAndAmountBetween() {
        User user = userRepository.save(new User(
                "Test User",
                UUID.randomUUID() + "@mail.com",
                "+79990000001"
        ));

        BankAccount account = bankAccountRepository.save(new BankAccount(
                "ACC-" + UUID.randomUUID(),
                new BigDecimal("1000.00"),
                "ACTIVE",
                user
        ));

        operationRepository.save(new Operation(
                "DEPOSIT",
                new BigDecimal("100.00"),
                LocalDateTime.now(),
                account
        ));

        operationRepository.save(new Operation(
                "DEPOSIT",
                new BigDecimal("250.00"),
                LocalDateTime.now(),
                account
        ));

        operationRepository.save(new Operation(
                "WITHDRAW",
                new BigDecimal("120.00"),
                LocalDateTime.now(),
                account
        ));

        List<Operation> result = operationRepository.findByTypeAndAmountBetween(
                "DEPOSIT",
                new BigDecimal("90.00"),
                new BigDecimal("150.00")
        );

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(new BigDecimal("100.00"), result.get(0).getAmount());
        Assertions.assertEquals("DEPOSIT", result.get(0).getType());
    }

    @Test
    void testFindByTypeAndAmountBetweenCriteria() {
        User user = userRepository.save(new User(
                "Test User",
                UUID.randomUUID() + "@mail.com",
                "+79990000002"
        ));

        BankAccount account = bankAccountRepository.save(new BankAccount(
                "ACC-" + UUID.randomUUID(),
                new BigDecimal("2000.00"),
                "ACTIVE",
                user
        ));

        operationRepository.save(new Operation(
                "PAYMENT",
                new BigDecimal("300.00"),
                LocalDateTime.now(),
                account
        ));

        operationRepository.save(new Operation(
                "PAYMENT",
                new BigDecimal("500.00"),
                LocalDateTime.now(),
                account
        ));

        operationRepository.save(new Operation(
                "DEPOSIT",
                new BigDecimal("350.00"),
                LocalDateTime.now(),
                account
        ));

        List<Operation> result = operationRepository.findByTypeAndAmountBetweenCriteria(
                "PAYMENT",
                new BigDecimal("250.00"),
                new BigDecimal("400.00")
        );

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(new BigDecimal("300.00"), result.get(0).getAmount());
        Assertions.assertEquals("PAYMENT", result.get(0).getType());
    }
}