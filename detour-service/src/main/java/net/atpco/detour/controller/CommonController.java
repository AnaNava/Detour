package net.atpco.detour.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CommonController {

	@RequestMapping("/")
    public String getStatus() {
        log.info("calling common controller for query status");
        return "Greetings from Spring Boot!";
    }

   

}
