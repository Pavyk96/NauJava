package ru.Daniil.NauJava.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Daniil.NauJava.config.AppConfig;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.UserRepository;
import ru.Daniil.NauJava.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final AppConfig appConfig;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, AppConfig appConfig, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.appConfig = appConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        System.out.println("Приложение: " + appConfig.getAppName());
        System.out.println("Версия: " + appConfig.getAppVersion());
    }

    @Override
    public User createUser(String fullName, String email, String phone, String password) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Имя не должно быть пустым");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email не должен быть пустым");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Телефон не должен быть пустым");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(hashedPassword);

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

    @Override
    public User getUserByUsername(String username) {
        return repository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с username=" + username + " не найден"));
    }
}