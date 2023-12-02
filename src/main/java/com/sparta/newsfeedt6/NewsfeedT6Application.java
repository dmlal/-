package com.sparta.newsfeedt6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NewsfeedT6Application {

	public static void main(String[] args) {
		SpringApplication.run(NewsfeedT6Application.class, args);
	}

}
