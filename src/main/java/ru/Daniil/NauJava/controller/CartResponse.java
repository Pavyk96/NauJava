package ru.Daniil.NauJava.controller;

import java.util.List;

/**
 * CartResponse
 *
 * @author Daniil Mezev
 */
public record CartResponse(
        Long id,
        String name,
        List<ItemResponse> items
) {
}
