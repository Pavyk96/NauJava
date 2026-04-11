package ru.Daniil.NauJava.repo.criteria;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Daniil.NauJava.model.BankCard;

import java.util.List;

/**
 * Репозиторий банковского аккаунта CriteriaAPI
 *
 * @author Daniil Mezev
 */
@RepositoryRestResource
public interface BankCardRepositoryCriteria {

    List<BankCard> findByUserEmailCriteria(String email);
}
