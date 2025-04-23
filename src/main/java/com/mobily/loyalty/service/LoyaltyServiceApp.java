package com.mobily.loyalty.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
/**
 * This is the main application for Loyalty Services
 * @author Mobily Info Tech (MIT)
 *
 */
@SpringBootApplication
@Slf4j
public class LoyaltyServiceApp {

	/**
	 * main method to start the services
	 * @param args
	 */
	public static void main(String[] args) {		
		log.info(" Loyalty service about to start");
		SpringApplication.run(LoyaltyServiceApp.class);
		log.info(" Loyalty services started successfully");
		log.info(" Loyalty services started successfully", keyValue("x-correlation-id", "12345"));
	}
}
