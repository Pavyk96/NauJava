package ru.Daniil.NauJava.repo;

import org.springframework.data.repository.CrudRepository;
import ru.Daniil.NauJava.model.BankAccount;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;

/**
 * Репозиторий банковского аккаунта
 *
 * @author Daniil Mezev
 */
@RepositoryRestResource
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
