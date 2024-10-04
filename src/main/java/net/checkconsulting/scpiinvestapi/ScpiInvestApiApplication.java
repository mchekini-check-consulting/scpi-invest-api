package net.checkconsulting.scpiinvestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScpiInvestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScpiInvestApiApplication.class, args);
	}

}
