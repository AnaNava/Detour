package net.atpco.detour.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.atpco.detour.service.SearchService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommonController {

	private final SearchService searchService;

	@RequestMapping("/")
    public String getStatus() {
        log.info("calling common controller for query status");
        return "Greetings from Spring Boot!";
    }



	@RequestMapping("/detour")
    public String detour() {
        log.info("calling search service");
        searchService.search();
        return "Results!";
    }

}
