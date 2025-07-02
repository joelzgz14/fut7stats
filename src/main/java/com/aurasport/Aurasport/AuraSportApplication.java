package com.aurasport.Aurasport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.Console;

/*@SpringBootApplication
public class AuraSportApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuraSportApplication.class, args);
	}

}*/

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // <-- AÑADE ESTA PARTE
public class AuraSportApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuraSportApplication.class, args);
	}



}
