package ru.Daniil.NauJava.repo;

import ru.Daniil.NauJava.model.User;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository
 *
 * @author Daniil Mezev
 */
public interface UserRepository {

    User create(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    User update(User user);

    boolean deleteById(Long id);
}
