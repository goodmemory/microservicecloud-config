package com.myq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.myq.dao")
@EnableTransactionManagement //开启事务
public class NewsManagementSystemApplication{

	public static void main(String[] args) {
		SpringApplication.run(NewsManagementSystemApplication.class, args);
	}
}

