package ru.Daniil.NauJava.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Банковская карта
 *
 * @author Daniil Mezev
 */
@Entity
@Table(name = "bank_cards")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "payment_system", nullable = false)
    private String paymentSystem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

    public BankCard() {
    }

    public BankCard(String cardNumber, LocalDate expirationDate, String paymentSystem, BankAccount bankAccount) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.paymentSystem = paymentSystem;
        this.bankAccount = bankAccount;
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(String paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}