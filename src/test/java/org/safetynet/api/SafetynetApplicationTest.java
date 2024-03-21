package org.safetynet.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@EnableConfigurationProperties
@SpringBootApplication
public class SafetynetApplicationTest {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SafetynetApplicationTest.class, args);
	}

}
