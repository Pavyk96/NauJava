package ru.Daniil.NauJava.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.Daniil.NauJava.model.User;
import ru.Daniil.NauJava.repo.BankCardRepository;
import ru.Daniil.NauJava.repo.BankAccountRepository;
import ru.Daniil.NauJava.repo.UserRepository;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LoginFlowSeleniumTest {

    private static final String GECKO_DRIVER_PATH = "/snap/bin/geckodriver";

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

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        bankCardRepository.deleteAll();
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();

        userRepository.save(new User("Test User", "test", "+79990000000", passwordEncoder.encode("test")));

        System.setProperty("webdriver.gecko.driver", GECKO_DRIVER_PATH);

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");

        driver = new FirefoxDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void userShouldLoginAndLogoutSuccessfully() {
        driver.get(baseUrl("/login"));

        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login-submit")).click();

        wait.until(ExpectedConditions.urlContains("/success"));
        WebElement successTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-title")));

        assertTrue(successTitle.getText().contains("успешно авторизованы"));

        driver.findElement(By.id("logout-button")).click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/login"),
                ExpectedConditions.urlContains("/logout")
        ));

        assertTrue(driver.getCurrentUrl().contains("/login"));
        assertTrue(driver.getPageSource().contains("Login"));
    }

    private String baseUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
