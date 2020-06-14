package net.atpco.detour.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableMongoRepositories
@SpringBootApplication(scanBasePackages = {"net.atpco.detour.*"})
@EnableAsync
public class DetourServiceApplication {

	public static void main(String[] args) {		
		SpringApplication.run(DetourServiceApplication.class, args);
	}
	

}
