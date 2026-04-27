package ru.Daniil.NauJava.controller;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.Daniil.NauJava.model.BankAccount;
import ru.Daniil.NauJava.model.BankCard;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.BankAccountRepository;
import ru.Daniil.NauJava.repo.BankCardRepository;
import ru.Daniil.NauJava.repo.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BankCardControllerRestAssuredTest {

    private static final Pattern CSRF_PATTERN = Pattern.compile("name=\"_csrf\" value=\"([^\"]+)\"");

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankCardRepository bankCardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        bankCardRepository.deleteAll();
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User("Test User", "test", "+79990000000", passwordEncoder.encode("test"));
        User savedUser = userRepository.save(user);

        BankAccount account = new BankAccount("ACC-001", new BigDecimal("1500.00"), "ACTIVE", savedUser);
        BankAccount savedAccount = bankAccountRepository.save(account);

        BankCard card = new BankCard("1111222233334444", LocalDate.of(2030, 1, 1), "VISA", savedAccount);
        bankCardRepository.save(card);
    }

    @Test
    void findByEmailShouldRedirectToLoginWhenUnauthorized() {
        given()
                .redirects().follow(false)
                .queryParam("email", "test")
        .when()
                .get("/custom/bankCards/findByEmail")
        .then()
                .statusCode(302)
                .header("Location", containsString("/login"));
    }

    @Test
    void findByEmailShouldReturnCardsForAuthorizedUser() {
        SessionFilter session = login();

        given()
                .filter(session)
                .accept(ContentType.JSON)
                .queryParam("email", "test")
        .when()
                .get("/custom/bankCards/findByEmail")
        .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].cardNumber", equalTo("1111222233334444"))
                .body("[0].paymentSystem", equalTo("VISA"));
    }

    @Test
    void findByEmailShouldReturnEmptyListForUnknownEmail() {
        SessionFilter session = login();

        given()
                .filter(session)
                .accept(ContentType.JSON)
                .queryParam("email", "unknown")
        .when()
                .get("/custom/bankCards/findByEmail")
        .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @Test
    void findByEmailShouldReturnBadRequestWhenEmailIsMissing() {
        SessionFilter session = login();

        given()
                .filter(session)
                .accept(ContentType.JSON)
        .when()
                .get("/custom/bankCards/findByEmail")
        .then()
                .statusCode(400);
    }

    private SessionFilter login() {
        SessionFilter session = new SessionFilter();

        String loginPage = given()
                .filter(session)
                .when()
                .get("/login")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        Matcher matcher = CSRF_PATTERN.matcher(loginPage);
        if (!matcher.find()) {
            throw new IllegalStateException("CSRF token not found on login page");
        }

        given()
                .filter(session)
                .contentType(ContentType.URLENC)
                .redirects().follow(false)
                .formParam("username", "test")
                .formParam("password", "test")
                .formParam("_csrf", matcher.group(1))
        .when()
                .post("/login")
        .then()
                .statusCode(302)
                .header("Location", containsString("/success"));

        return session;
    }
}
