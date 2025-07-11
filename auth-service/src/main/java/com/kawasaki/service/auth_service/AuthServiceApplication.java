package com.kawasaki.service.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.kawasaki.service.auth_service.feign")
@SpringBootApplication(
	exclude = {DataSourceAutoConfiguration.class},
	scanBasePackages = {
		"com.kawasaki.service.auth_service",
		"com.kawasaki.service.common"
	})
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
