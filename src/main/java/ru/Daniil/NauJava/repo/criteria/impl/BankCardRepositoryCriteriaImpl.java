package ru.Daniil.NauJava.repo.criteria.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import ru.Daniil.NauJava.model.BankAccount;
import ru.Daniil.NauJava.model.BankCard;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.criteria.BankCardRepositoryCriteria;

import java.util.List;

/**
 * BankCardRepositoryCriteriaImpl
 *
 * @author Daniil Mezev
 */
public class BankCardRepositoryCriteriaImpl implements BankCardRepositoryCriteria {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BankCard> findByUserEmailCriteria(String email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BankCard> query = cb.createQuery(BankCard.class);
        Root<BankCard> root = query.from(BankCard.class);

        Join<BankCard, BankAccount> accountJoin = root.join("bankAccount");
        Join<BankAccount, User> userJoin = accountJoin.join("user");

        query.select(root).where(cb.equal(userJoin.get("email"), email));

        return entityManager.createQuery(query).getResultList();
    }
}
