package com.example.apibasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApibasicApplication {

	public static void main(String[] args) {
		int a = 10;
		System.out.println(a);
		int b = 10;
		System.out.println(b);
		int c = 20;
		System.out.println(c);
		SpringApplication.run(ApibasicApplication.class, args);
	}

}
