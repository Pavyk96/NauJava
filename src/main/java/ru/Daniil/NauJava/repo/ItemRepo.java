package ru.Daniil.NauJava.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Daniil.NauJava.model.Item;

/**
 * Item
 *
 * @author Daniil Mezev
 */
@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
}
