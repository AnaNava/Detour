package net.atpco.detour.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.atpco.detour.model.DetourRequest;
import net.atpco.detour.model.DetourResponse;
import net.atpco.detour.model.PricingSolution;
import net.atpco.detour.repository.DetourRepository;
import net.atpco.detour.util.DataLoader;

@Service
@AllArgsConstructor
public class DetourService {

	private DetourRepository detourRepository;
	
	public DetourResponse processRequest(DetourRequest detourReq) {
		
		DetourResponse response = getDetourResponse(detourReq);
//		detourRepository.insert(detourReq, "RequestCoolection");
		return response;
	}

	private DetourResponse getDetourResponse(DetourRequest detourReq) {
		DetourResponse response = new DetourResponse();
		response.setRequest(detourReq);
		DataLoader loader = new DataLoader();
		try {
			List<PricingSolution> solutions = loader.load();
			response.setSolutions(solutions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return response;
	}

}
