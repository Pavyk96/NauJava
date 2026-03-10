package ru.Daniil.NauJava.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.Daniil.NauJava.config.AppConfig;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.UserRepository;
import ru.Daniil.NauJava.service.UserService;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author Daniil Mezev
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final AppConfig appConfig;

    public UserServiceImpl(UserRepository repository, AppConfig appConfig) {
        this.repository = repository;
        this.appConfig = appConfig;
    }

    @PostConstruct
    public void init() {
        System.out.println("Приложение: " + appConfig.getAppName());
        System.out.println("Версия: " + appConfig.getAppVersion());
    }

    @Override
    public User createUser(String name, int balance) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя не должно быть пустым");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Баланс не может быть отрицательным");
        }

        User user = new User();
        user.setName(name);
        user.setBalance(balance);

        return repository.create(user);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id=" + id + " не найден"));
    }

    @Override
    public void transferMoney(Long fromUserId, Long toUserId, int amount) {
        if (fromUserId.equals(toUserId)) {
            throw new IllegalArgumentException("Нельзя переводить деньги самому себе");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть больше 0");
        }

        User fromUser = getUserById(fromUserId);
        User toUser = getUserById(toUserId);

        if (fromUser.getBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств для перевода");
        }

        fromUser.setBalance(fromUser.getBalance() - amount);
        toUser.setBalance(toUser.getBalance() + amount);

        repository.update(fromUser);
        repository.update(toUser);
    }
}
