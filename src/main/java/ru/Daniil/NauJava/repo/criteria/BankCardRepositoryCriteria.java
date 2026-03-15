package ru.Daniil.NauJava.repo.criteria;

import ru.Daniil.NauJava.model.BankCard;

import java.util.List;

/**
 * Репозиторий банковского аккаунта CriteriaAPI
 *
 * @author Daniil Mezev
 */
public interface BankCardRepositoryCriteria {

    List<BankCard> findByUserEmailCriteria(String email);
}
