package com.poc.sdkpoc;

import com.rcx.client.model.RCXToken;
import com.rcx.client.service.AuthHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(value = { "com.rcx.client", "com.poc.sdkpoc" })
@Slf4j
public class SdkpocApplication {

	private final @NonNull AuthHandler authHandler;

	public static void main(String[] args) {
		SpringApplication.run(SdkpocApplication.class, args);
		log.info("Application is running");
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return s -> {
			RCXToken rcxToken = null;
			try {
				rcxToken = authHandler.login();
			} catch (Exception e) {
				System.out.println("Exception " + e);
			}

			log.info("Token " + rcxToken.getToken());
		};
	}

}
