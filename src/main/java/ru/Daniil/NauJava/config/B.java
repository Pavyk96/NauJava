package ru.Daniil.NauJava.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

/**
 * B
 *
 * @author Daniil Mezev
 */
@Service
public class B {

    private final ObjectProvider<A> aObjectProvider;

    private B(ObjectProvider<A> aObjectProvider) {
        this.aObjectProvider = aObjectProvider;
    }

    public void useA() {
        A a = aObjectProvider.getIfAvailable();
    }
}
