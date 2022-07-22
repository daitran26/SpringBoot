package com.spring.baitap10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Baitap10Application {

	public static void main(String[] args) {
		Environment env = SpringApplication.run(Baitap10Application.class, args).getEnvironment();
		String appName = env.getProperty("spring.application.name").toUpperCase();
		String port = env.getProperty("server.port");
		System.out.println("-------------------------START " + appName + " Application------------------------------");
		System.out.println("   Application         : " + appName);
		System.out.println("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
		System.out.println("-------------------------START SUCCESS " + appName + " Application------------------------------");
	}
}
