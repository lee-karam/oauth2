package org.spring.springbootoauth2_ex2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Springbootoauth2Ex2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springbootoauth2Ex2Application.class, args);
	}

}
