package net.atpco.detour.service;

import org.springframework.stereotype.Service;

import net.atpco.detour.model.DetourRequest;
import net.atpco.detour.model.DetourResponse;

@Service
public class DetourService {
	
	public DetourResponse processRequest(DetourRequest detourReq) {
		return new DetourResponse();
	}

}
