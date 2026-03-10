package ru.Daniil.NauJava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.Daniil.NauJava.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Иммитация БД
 *
 * @author Daniil Mezev
 */
@Configuration
public class DatabaseConfig {

    @Bean
    public List<User> userStorage() {
        return new ArrayList<>();
    }
}
