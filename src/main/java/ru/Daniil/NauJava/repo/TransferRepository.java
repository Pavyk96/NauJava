package ru.Daniil.NauJava.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Daniil.NauJava.model.Transfer;

/**
 * Репозиторий переводов между счетов
 *
 * @author Daniil Mezev
 */
@RepositoryRestResource
public interface TransferRepository extends CrudRepository<Transfer, Long> {
}
