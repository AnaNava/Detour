package net.atpco.detour.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.atpco.detour.service.SearchService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ResearchController {

	private final SearchService searchService;

	@RequestMapping("/country")
    public ResponseEntity<String> processRequest(String carrier) {
        log.info("DetourController - processRequest");
        HttpEntity<String> response =  searchService.getCountryData(carrier);
        return ResponseEntity.ok("OK");
    }

   
}
