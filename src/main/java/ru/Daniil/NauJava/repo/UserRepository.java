package ru.Daniil.NauJava.repo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Daniil.NauJava.model.User;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
/**
 * Репозиторий пользователей
 *
 * @author Daniil Mezev
 */
@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

}