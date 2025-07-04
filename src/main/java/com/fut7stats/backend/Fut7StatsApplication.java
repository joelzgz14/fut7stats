package com.fut7stats.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/*@SpringBootApplication
public class AuraSportApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuraSportApplication.class, args);
	}

}*/

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // <-- AÑADE ESTA PARTE
public class Fut7StatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Fut7StatsApplication.class, args);
	}



}
