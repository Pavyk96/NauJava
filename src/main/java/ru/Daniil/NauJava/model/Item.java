package ru.Daniil.NauJava.model;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * Item
 *
 * @author Daniil Mezev
 */
@Entity
@Getter
public class Item {
    @Id
    private Long id;

    private String name;

    @ManyToOne()
    private Cart cart;
}
