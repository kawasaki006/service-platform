package com.kawasaki.service.order_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.kawasaki.service.order_service.mapper")
@SpringBootApplication(
    scanBasePackages = {
        "com.kawasaki.service.order_service",
        "com.kawasaki.service.common"
    }
)
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
