package se.cleancode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CqrsexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CqrsexampleApplication.class, args);
	}
}
