package com.kawasaki.service.user_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.kawasaki.service.user_service.mapper")
@SpringBootApplication(
    scanBasePackages = {
        "com.kawasaki.service.user_service",
        "com.kawasaki.service.common"
    }
)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
