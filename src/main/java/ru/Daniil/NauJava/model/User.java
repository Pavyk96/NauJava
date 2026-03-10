package ru.Daniil.NauJava.model;

/**
 * Пользователь
 *
 * @author Daniil Mezev
 */
public class User {

    private Long id;
    private String name;
    private int balance;

    public User() {
    }

    public User(Long id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', balance=" + balance + "}";
    }
}
