package se.cleancode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration
public class CqrsexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CqrsexampleApplication.class, args);
	}
}
