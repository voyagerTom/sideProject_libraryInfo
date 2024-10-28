package com.example.processData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling	//開啟排程功能
public class ProcessDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessDataApplication.class, args);
	}

}
