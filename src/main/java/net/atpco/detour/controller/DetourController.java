package net.atpco.detour.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.atpco.detour.model.DetourRequest;
import net.atpco.detour.model.DetourResponse;
import net.atpco.detour.service.DetourService;

@RestController
@RequestMapping("/api/vi")
@Slf4j
@RequiredArgsConstructor
public class DetourController {
	
	private final DetourService detourService;

	@RequestMapping("/detour")
    public ResponseEntity<DetourResponse> processRequest(@Valid @RequestBody final DetourRequest detourRequest) {
        log.info("DetourController - processRequest");
        DetourResponse response =  detourService.processRequest(detourRequest);
        return ResponseEntity.ok(response);
    }

   

}
