package ru.Daniil.NauJava.service;

import ru.Daniil.NauJava.model.User;

import java.util.List;

/**
 * Сервис пользователей
 *
 * @author Daniil Mezev
 */
public interface UserService {

    User createUser(String fullName, String email, String phone, String password);

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);
}