package com.kawasaki.service.service_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.kawasaki.service.service_service.mapper")
@SpringBootApplication
public class ServiceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceServiceApplication.class, args);
	}

}
