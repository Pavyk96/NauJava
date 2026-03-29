package ru.Daniil.NauJava.service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Сервис аккаунтов
 *
 * @author Daniil Mezev
 */
public interface AccountService {

    void createAccountWithCard(Long userId,
                               String accountNumber,
                               BigDecimal balance,
                               String accountStatus,
                               String cardNumber,
                               LocalDate expirationDate,
                               String paymentSystem);
}