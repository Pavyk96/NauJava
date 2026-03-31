package ru.Daniil.NauJava.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.Daniil.NauJava.model.Cart;

import java.util.List;
import java.util.Optional;

/**
 * CartRepo
 *
 * @author Daniil Mezev
 */
@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items")
    List<Cart> findAll();

//    @EntityGraph(attributePaths = "items")
//    List<Cart> findAll();
//
//    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.id = :id")
//    Optional<Cart> findByIdWithItems(@Param("id") Long id);
}
