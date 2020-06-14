package net.atpco.detour.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.atpco.detour.model.CountryInfo;
import net.atpco.detour.model.DetourRequest;
import net.atpco.detour.model.DetourResponse;
import net.atpco.detour.model.PricingSolution;
import net.atpco.detour.repository.DetourRepository;
import net.atpco.detour.util.DataLoader;

@Service
@AllArgsConstructor
@Slf4j
public class DetourService {

	private DetourRepository detourRepository;
	private static Map<String, List<String>> destinationMap = null;
	private SearchService searchService;

	public DetourResponse processRequest(DetourRequest detourReq) {
		DetourResponse response = getDetourResponse(detourReq);	// detourRepository.insert(detourReq, "RequestCoolection");
		return response;
	}

	private DetourResponse getDetourResponse(DetourRequest detourReq) {
		DetourResponse response = new DetourResponse();
		response.setRequest(detourReq);

		DataLoader loader = new DataLoader();
		try {
			if (destinationMap == null) {
				destinationMap = loader.loadCityPair();
				log.info("Destination map {}", destinationMap);
			}
			List<String> destinations = destinationMap.get(detourReq.getOrigin());
			log.info("destinations {}", destinations);
			
			if (destinations == null) return response;
			int pricingSolIndex = 0;
			List<PricingSolution> solutions = new ArrayList<>();
			for (String destination : destinations) {
				log.info("Data Loading {}, {}", detourReq.getOrigin(), destination);
				List<PricingSolution> sols = loader.loadPS(detourReq.getOrigin(), destination, detourRepository, pricingSolIndex);
				if (sols == null) {
					//Call Shopping API
					searchService.getShoppingResponse(detourReq, destination);
				}
				pricingSolIndex +=sols.size();
				solutions.addAll(sols);

			}
			response.setSolutions(solutions);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

}