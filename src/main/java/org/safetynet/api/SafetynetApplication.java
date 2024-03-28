package org.safetynet.api;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SafetynetApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SafetynetApplication.class, args);
	}

}
