package ru.Daniil.NauJava.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализовать интерфейс Iterator для массива
 */
@RequiredArgsConstructor
public class ArrayIterator<T> implements Iterator<T> {

    private final T[] array;
    private int currentIndex = 0; //в зависимсоти от того, какой у нас массив, мб Лонг стоавить нужно

    @Override
    public boolean hasNext() {
        return currentIndex < array.length;
    }

    @Override
    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return array[currentIndex++];
    }

    public static void main(String... args) {
        final Object obj1 = new Object();
        final Object obj2 = new Object();

        System.out.println(obj1.equals(obj2)); //true -
        System.out.println(obj1 == obj2); //false

        final String str1 = new String("string");
        final String str2 = new String("string");

        System.out.println(str1.equals(str2)); //true
        System.out.println(str1 == str2); //false

        final String str3 = "string";
        final String str4 = "string";

        System.out.println(str3.equals(str4)); //true
        System.out.println(str3 == str4); //true

        final Integer i1 = new Integer(1);
        final Integer i2 = new Integer(1);

        System.out.println(i1.equals(i2)); //true
        System.out.println(i1 == i2); //false

        final Integer i3 = 128;
        final Integer i4 = 128;

        System.out.println(i3.equals(i4)); //true
        System.out.println(i3 == i4); //true -


        Long l1 = 128L;
        System.out.println(l1.equals(128)); //false
    }
}

