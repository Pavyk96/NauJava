package ru.Daniil.NauJava.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.Daniil.NauJava.config.AppConfig;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.UserRepository;
import ru.Daniil.NauJava.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса пользователей
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
    public User createUser(String fullName, String email, String phone) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Имя не должно быть пустым");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email не должен быть пустым");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Телефон не должен быть пустым");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);

        return repository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id=" + id + " не найден"));
    }
}