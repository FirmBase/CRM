package com.hlbs.crm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class CrmApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return runner -> {
			log.debug("Application started.");
		};
	}
}
