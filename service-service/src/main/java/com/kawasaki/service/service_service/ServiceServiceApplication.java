package com.kawasaki.service.service_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.kawasaki.service.service_service.mapper")
@EnableFeignClients(basePackages = "com.kawasaki.service.service_service.feign")
@SpringBootApplication
public class ServiceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceServiceApplication.class, args);
	}

}
