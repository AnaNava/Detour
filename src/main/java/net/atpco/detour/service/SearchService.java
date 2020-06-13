package net.atpco.detour.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

	private final String countryUrl = "https://www.sitata.com/api/v2/countries/countryid/travel_restrictions";

	public void search() {
		// Call shopping engine API

		// Call Retailing API for amenities, uta and upas

	}

	public HttpEntity<String> getCountryData(String countryCode) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("AUTHORIZATION", "TKN VXNlcnw1YWQ5NDNlMzdhZmY1YTBlZTlmNDk4ZmF8V21LaFJXeDM2SG9hRWV6Yi1uVVI=");
		headers.add("Content-Type", "application/json");

		log.info("Sending country request for  {} to {} ", countryCode, countryUrl);

		String url = countryUrl.replace("countryid", countryCode);

		log.info("After replace {} to {} ", url);

		HttpEntity<String> response = callAPI(restTemplate, headers, url);

		return response;
	}

	private HttpEntity<String> callAPI(RestTemplate restTemplate, MultiValueMap<String, String> headers, String countryUrl) {
		HttpEntity<String> request = new HttpEntity<>("", headers);
		ResponseEntity<String> response = null;
		try {
			log.info("Sending country request for  {} ", countryUrl);

			response = restTemplate.exchange(countryUrl, HttpMethod.GET, request, String.class);
			
			log.info(response.getBody());

			if (response.getStatusCodeValue() != 200) {
				log.error("Error invoking UPA service, returned {} \n{}", response.getStatusCodeValue(),
						response.getBody());
			}

		} catch (Exception ex) {
			log.error("Error executing the UPA query", ex);
		}
		return response;
	}
}
