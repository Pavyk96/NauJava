package ru.Daniil.NauJava.repo.impl;

import org.springframework.stereotype.Repository;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Реализация репозитория
 *
 * @author Daniil Mezev
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final List<User> userStorage;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public UserRepositoryImpl(List<User> userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User не должен быть null");
        }

        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }

        userStorage.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userStorage.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStorage);
    }

    @Override
    public User update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User не должен быть null");
        }

        for (int i = 0; i < userStorage.size(); i++) {
            User currentUser = userStorage.get(i);

            if (Objects.equals(currentUser.getId(), user.getId())) {
                userStorage.set(i, user);
                return user;
            }
        }

        throw new IllegalArgumentException("Пользователь с id=" + user.getId() + " не найден");
    }

    @Override
    public boolean deleteById(Long id) {
        return userStorage.removeIf(user -> Objects.equals(user.getId(), id));
    }
}