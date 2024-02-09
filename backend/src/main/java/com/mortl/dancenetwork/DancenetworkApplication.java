package com.mortl.dancenetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DancenetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DancenetworkApplication.class, args);
	}

}
