package com.yq.shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yq.shopping.mapper")
public class AppSolr {

	public static void main(String[] args) {
		SpringApplication.run(AppSolr.class, args);
	}

}
