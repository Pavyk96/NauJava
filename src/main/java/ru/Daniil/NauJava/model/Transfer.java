package ru.Daniil.NauJava.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Перевод между счетами
 *
 * @author Daniil Mezev
 */
@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id", nullable = false)
    private BankAccount senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_account_id", nullable = false)
    private BankAccount receiverAccount;

    public Transfer() {
    }

    public Transfer(BigDecimal amount, LocalDateTime createdAt, String status,
                    BankAccount senderAccount, BankAccount receiverAccount) {
        this.amount = amount;
        this.createdAt = createdAt;
        this.status = status;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BankAccount getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(BankAccount senderAccount) {
        this.senderAccount = senderAccount;
    }

    public BankAccount getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(BankAccount receiverAccount) {
        this.receiverAccount = receiverAccount;
    }
}