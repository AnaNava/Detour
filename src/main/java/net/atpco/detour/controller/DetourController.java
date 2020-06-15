package net.atpco.detour.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "/detour", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetourResponse> processRequest(@Valid @RequestBody final DetourRequest detourRequest) {
        log.info("DetourController - processRequest");
        DetourResponse response =  detourService.processRequest(detourRequest);
        return ResponseEntity.ok(response);
    }
	
	/*
	 * "origin":"WAS", "prefDestinaionType":"Beach", "travelFrom":"5Dec",
	 * "travelTo":"10Dec", "travelPrefType":"Anything",
	 * "travelType":"International", "distance":"5hrs"
	 */
	@RequestMapping(value = "/detour", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DetourResponse> processRequest() {
		DetourRequest detourRequest = new DetourRequest();
		detourRequest.setDistance("5hrs");
		detourRequest.setOrigin("WAS");
		detourRequest.setTravelFrom("5Dec");
		detourRequest.setTravelTo("10Dec");
		detourRequest.setTravelPrefType("Anything");
		detourRequest.setPrefDestinaionType("Beach");
		detourRequest.setTravelType("International");
		DetourResponse response = detourService.processRequest(detourRequest);
		return ResponseEntity.ok(response);
	}

   

}
