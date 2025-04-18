package com.emp1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class App1Application {
	
	@Value("${server.port}")
	public static String serverport;

	public static void main(String[] args) {
		 ConfigurableApplicationContext context = SpringApplication.run(App1Application.class, args);

	        Environment env = context.getEnvironment();
	        String port = env.getProperty("server.port");

	        System.out.println("Server is running at port: " + port);
	}

}
