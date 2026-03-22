package ru.Daniil.NauJava.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Daniil.NauJava.model.Operation;
import ru.Daniil.NauJava.repo.criteria.OperationRepositoryCriteria;

import java.math.BigDecimal;
import java.util.List;

/**
 * Репозиторий операций
 *
 * @author Daniil Mezev
 */
@RepositoryRestResource
public interface OperationRepository extends CrudRepository<Operation, Long>, OperationRepositoryCriteria {

    List<Operation> findByTypeAndAmountBetween(String type, BigDecimal minAmount, BigDecimal maxAmount);
}
