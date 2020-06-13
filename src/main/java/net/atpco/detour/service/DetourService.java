package net.atpco.detour.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.atpco.detour.model.DetourRequest;
import net.atpco.detour.model.DetourResponse;
import net.atpco.detour.model.PricingSolution;
import net.atpco.detour.repository.DetourRepository;

@Service
@AllArgsConstructor
public class DetourService {

	private DetourRepository detourRepository;
	
	public DetourResponse processRequest(DetourRequest detourReq) {
		
		DetourResponse response = getDetourResponse(detourReq);
		detourRepository.insert(detourReq, "RequestCoolection");
		return response;
	}

	private DetourResponse getDetourResponse(DetourRequest detourReq) {
		DetourResponse response = new DetourResponse();
		response.setRequest(detourReq);
		PricingSolution sol1 = new PricingSolution();
		return response;
	}

}
