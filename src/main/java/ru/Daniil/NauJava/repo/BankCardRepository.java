package ru.Daniil.NauJava.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.Daniil.NauJava.model.BankCard;
import ru.Daniil.NauJava.repo.criteria.BankCardRepositoryCriteria;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий банковских карт
 *
 * @author Daniil Mezev
 */
public interface BankCardRepository extends CrudRepository<BankCard, Long>, BankCardRepositoryCriteria {

    Optional<BankCard> findByCardNumber(String cardNumber);

    @Query("select c from BankCard c where c.bankAccount.user.email = :email")
    List<BankCard> findByUserEmail(@Param("email") String email);
}
