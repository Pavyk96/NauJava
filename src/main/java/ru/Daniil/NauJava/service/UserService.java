package ru.Daniil.NauJava.service;

import ru.Daniil.NauJava.model.User;

import java.util.List;

/**
 * UserService
 *
 * @author Daniil Mezev
 */
public interface UserService {

    User createUser(String name, int balance);

    List<User> getAllUsers();

    User getUserById(Long id);

    void transferMoney(Long fromUserId, Long toUserId, int amount);
}
