package ru.Daniil.NauJava.repo.criteria;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Daniil.NauJava.model.Operation;

import java.math.BigDecimal;
import java.util.List;

/**
 * Репозиторий операций CriteriaAPI
 *
 * @author Daniil Mezev
 */
@RepositoryRestResource
public interface OperationRepositoryCriteria {

    List<Operation> findByTypeAndAmountBetweenCriteria(String type, BigDecimal minAmount, BigDecimal maxAmount);
}
