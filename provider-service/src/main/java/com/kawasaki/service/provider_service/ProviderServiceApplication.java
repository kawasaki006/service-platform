package com.kawasaki.service.provider_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.kawasaki.service.provider_service.mapper")
@SpringBootApplication(scanBasePackages = {
		"com.kawasaki.service.provider_service",
		"com.kawasaki.service.common"
})
public class ProviderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderServiceApplication.class, args);
	}

}
