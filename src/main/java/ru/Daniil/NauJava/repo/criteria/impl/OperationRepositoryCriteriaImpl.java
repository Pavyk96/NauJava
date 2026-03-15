package ru.Daniil.NauJava.repo.criteria.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.Daniil.NauJava.model.Operation;
import ru.Daniil.NauJava.repo.criteria.OperationRepositoryCriteria;

import java.math.BigDecimal;
import java.util.List;

/**
 * OperationRepositoryCriteriaImpl
 *
 * @author Daniil Mezev
 */
public class OperationRepositoryCriteriaImpl implements OperationRepositoryCriteria {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Operation> findByTypeAndAmountBetweenCriteria(String type, BigDecimal minAmount, BigDecimal maxAmount) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Operation> query = cb.createQuery(Operation.class);
        Root<Operation> root = query.from(Operation.class);

        query.select(root).where(
                cb.and(
                        cb.equal(root.get("type"), type),
                        cb.between(root.get("amount"), minAmount, maxAmount)
                )
        );

        return entityManager.createQuery(query).getResultList();
    }
}
