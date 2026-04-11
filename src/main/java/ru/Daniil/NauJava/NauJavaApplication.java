package ru.Daniil.NauJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@EnableAspectJAutoProxy
public class NauJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NauJavaApplication.class, args);
	}
}