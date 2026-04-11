package ru.Daniil.NauJava.service;

import ru.Daniil.NauJava.model.Item;

import java.util.List;

/**
 * ItemService
 *
 * @author Daniil Mezev
 */
public interface ItemService {
    Item getItemById(Long id);
    Item createItem(Item item);
    List<Item> getAllItems();
}
