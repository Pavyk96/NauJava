package ru.Daniil.NauJava.repo;

import org.springframework.data.repository.CrudRepository;
import ru.Daniil.NauJava.model.Transfer;

/**
 * Репозиторий переводов между счетов
 *
 * @author Daniil Mezev
 */
public interface TransferRepository extends CrudRepository<Transfer, Long> {
}
