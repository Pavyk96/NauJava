package ru.Daniil.NauJava.repo;

import ru.Daniil.NauJava.model.User;
import java.util.Optional;

/**
 * Репозиторий пользователей
 *
 * @author Daniil Mezev
 */
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}