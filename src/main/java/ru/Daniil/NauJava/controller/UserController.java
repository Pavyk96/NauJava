package ru.Daniil.NauJava.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.service.UserService;

import java.util.List;
import java.util.Scanner;

/**
 * Консольная часть приложения
 *
 * @author Daniil Mezev
 */
@Component
public class UserController implements CommandLineRunner {

    private final UserService userService;
    private final ConfigurableApplicationContext context;

    public UserController(UserService userService, ConfigurableApplicationContext context) {
        this.userService = userService;
        this.context = context;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String command = scanner.nextLine();

            try {
                switch (command) {
                    case "1" -> createUser(scanner);
                    case "2" -> showAllUsers();
                    case "3" -> showUserById(scanner);
                    case "4" -> transferMoney(scanner);
                    case "0" -> exitApplication();
                    default -> System.out.println("Неизвестная команда");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1 - Создать пользователя");
        System.out.println("2 - Показать всех пользователей");
        System.out.println("3 - Найти пользователя по id");
        System.out.println("4 - Перевести деньги");
        System.out.println("0 - Выход");
        System.out.print("Введите команду: ");
    }

    private void createUser(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String name = scanner.nextLine();

        System.out.print("Введите начальный баланс: ");
        int balance = Integer.parseInt(scanner.nextLine());

        User user = userService.createUser(name, balance);
        System.out.println("Пользователь создан: " + user);
    }

    private void showAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст");
            return;
        }

        for (User user : users) {
            System.out.println(user);
        }
    }

    private void showUserById(Scanner scanner) {
        System.out.print("Введите id пользователя: ");
        Long id = Long.parseLong(scanner.nextLine());

        User user = userService.getUserById(id);
        System.out.println("Найден пользователь: " + user);
    }

    private void transferMoney(Scanner scanner) {
        System.out.print("Введите id отправителя: ");
        Long fromUserId = Long.parseLong(scanner.nextLine());

        System.out.print("Введите id получателя: ");
        Long toUserId = Long.parseLong(scanner.nextLine());

        System.out.print("Введите сумму перевода: ");
        int amount = Integer.parseInt(scanner.nextLine());

        userService.transferMoney(fromUserId, toUserId, amount);
        System.out.println("Перевод выполнен");
    }

    private void exitApplication() {
        System.out.println("Приложение завершено");
        context.close();
        System.exit(0);
    }
}