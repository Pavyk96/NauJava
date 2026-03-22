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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Тесты для репозитория банковских карт
 *
 * @author Daniil Mezev
 */
@SpringBootTest
class BankCardRepositoryTest {

    @Autowired
    private BankCardRepository bankCardRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        bankCardRepository.deleteAll();
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testFindByUserEmail() {
        User user1 = userRepository.save(new User(
                "Ivan Ivanov",
                "ivan-" + UUID.randomUUID() + "@mail.com",
                "+79990000003"
        ));

        User user2 = userRepository.save(new User(
                "Petr Petrov",
                "petr-" + UUID.randomUUID() + "@mail.com",
                "+79990000004"
        ));

        BankAccount account1 = bankAccountRepository.save(new BankAccount(
                "ACC-" + UUID.randomUUID(),
                new BigDecimal("5000.00"),
                "ACTIVE",
                user1
        ));

        BankAccount account2 = bankAccountRepository.save(new BankAccount(
                "ACC-" + UUID.randomUUID(),
                new BigDecimal("7000.00"),
                "ACTIVE",
                user2
        ));

        BankCard card1 = bankCardRepository.save(new BankCard(
                "1111222233334444",
                LocalDate.now().plusYears(2),
                "VISA",
                account1
        ));

        bankCardRepository.save(new BankCard(
                "5555666677778888",
                LocalDate.now().plusYears(3),
                "MASTERCARD",
                account2
        ));

        List<BankCard> result = bankCardRepository.findByUserEmail(user1.getEmail());

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(card1.getCardNumber(), result.get(0).getCardNumber());
    }

    @Test
    void testFindByUserEmailCriteria() {
        User user1 = userRepository.save(new User(
                "Sergey Sergeev",
                "sergey-" + UUID.randomUUID() + "@mail.com",
                "+79990000005"
        ));

        User user2 = userRepository.save(new User(
                "Alex Alexeev",
                "alex-" + UUID.randomUUID() + "@mail.com",
                "+79990000006"
        ));

        BankAccount account1 = bankAccountRepository.save(new BankAccount(
                "ACC-" + UUID.randomUUID(),
                new BigDecimal("8000.00"),
                "ACTIVE",
                user1
        ));

        BankAccount account2 = bankAccountRepository.save(new BankAccount(
                "ACC-" + UUID.randomUUID(),
                new BigDecimal("9000.00"),
                "ACTIVE",
                user2
        ));

        BankCard card1 = bankCardRepository.save(new BankCard(
                "9999000011112222",
                LocalDate.now().plusYears(1),
                "MIR",
                account1
        ));

        bankCardRepository.save(new BankCard(
                "3333444455556666",
                LocalDate.now().plusYears(1),
                "VISA",
                account2
        ));

        List<BankCard> result = bankCardRepository.findByUserEmailCriteria(user1.getEmail());

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(card1.getCardNumber(), result.get(0).getCardNumber());
    }
}