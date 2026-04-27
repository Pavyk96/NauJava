package ru.Daniil.NauJava.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.Daniil.NauJava.model.BankAccount;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий банковского аккаунта
 *
 * @author Daniil Mezev
 */
@RepositoryRestResource
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountNumber(String accountNumber);

    @Query("select ba from BankAccount ba join fetch ba.user")
    List<BankAccount> findAllWithUser();
}
