package com.example.tiktok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TiktokApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TiktokApplication.class, args);
	}

}
