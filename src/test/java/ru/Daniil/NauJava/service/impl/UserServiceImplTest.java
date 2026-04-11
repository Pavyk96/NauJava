package ru.Daniil.NauJava.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.Daniil.NauJava.config.AppConfig;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private AppConfig appConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void createUserShouldEncodePasswordAndSaveUser() {
        when(passwordEncoder.encode("secret")).thenReturn("encoded-secret");
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.createUser("Test User", "test", "+79990000000", "secret");

        verify(repository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertNotNull(result);
        assertEquals("Test User", savedUser.getFullName());
        assertEquals("test", savedUser.getEmail());
        assertEquals("+79990000000", savedUser.getPhone());
        assertEquals("encoded-secret", savedUser.getPassword());
    }

    @Test
    void createUserShouldThrowWhenFullNameIsBlank() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser("  ", "test", "+79990000000", "secret")
        );

        assertEquals("Имя не должно быть пустым", exception.getMessage());
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void getUserByIdShouldReturnExistingUser() {
        User user = new User("Test User", "test", "+79990000000", "encoded-secret");
        when(repository.findById(10L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(10L);

        assertSame(user, result);
    }

    @Test
    void getUserByUsernameShouldThrowWhenUserDoesNotExist() {
        when(repository.findByEmail("missing")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUserByUsername("missing")
        );

        assertEquals("Пользователь с username=missing не найден", exception.getMessage());
    }
}
