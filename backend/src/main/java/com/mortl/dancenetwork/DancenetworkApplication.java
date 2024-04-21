package com.mortl.dancenetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// TODO use openrewrite to enforce naming conventions
// TODO configure and store/save code style config file
// TODO CORS (https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS/Errors/CORSMissingAllowOrigin?utm_source=devtools&utm_medium=firefox-cors-errors&utm_campaign=default)
// TODO CSRF
public class DancenetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DancenetworkApplication.class, args);
	}

}
