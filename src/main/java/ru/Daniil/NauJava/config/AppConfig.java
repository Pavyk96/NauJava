package ru.Daniil.NauJava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AppConfig
 *
 * @author Daniil Mezev
 */
@Configuration
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService reportExecutorService() {
        return Executors.newFixedThreadPool(4);
    }
}
