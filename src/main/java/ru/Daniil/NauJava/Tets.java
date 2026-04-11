package ru.Daniil.NauJava;

import java.util.ArrayList;
import java.util.List;

/**
 * Tets
 *
 * @author Daniil Mezev
 */
public class Tets {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        while (list.size() > 1) {
            list.remove(1);
        }
    }
}
