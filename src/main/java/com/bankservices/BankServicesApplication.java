package com.bankservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankServicesApplication {
	private static final Logger logger = LoggerFactory.getLogger(BankServicesApplication.class);
	public static void main(String[] args) {

		logger.info("BankServicesApplication starting .....");
		SpringApplication.run(BankServicesApplication.class, args);
	}

}
