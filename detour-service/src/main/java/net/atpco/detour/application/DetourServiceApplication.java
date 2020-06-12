package net.atpco.detour.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"net.atpco.detour"})
public class DetourServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetourServiceApplication.class, args);
	}

}
