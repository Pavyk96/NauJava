package ru.Daniil.NauJava.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Cart
 *
 * @author Daniil Mezev
 */
@Entity
@Setter
@Getter
@Table(
        indexes = @Index(name = "idx_cart_name", columnList = "name")
)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private List<Item> items;

}
