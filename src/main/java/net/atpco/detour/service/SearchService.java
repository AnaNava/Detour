package net.atpco.detour.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.atpco.detour.model.Country;
import net.atpco.detour.repository.DetourRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

	private DetourRepository detourRepository;

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

		ResponseEntity<String> response = callAPI(restTemplate, headers, url);

		Document myDoc = parseJSON(response);
		myDoc.append("COUNTRY_CODE", countryCode);

		// saveJSONtoDB(myDoc, "COUNTRY_RES");

		return response;
	}

	private Document parseJSON(ResponseEntity<String> response) {

		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Country[] country = objectMapper.readValue(response.getBody(), Country[].class);

			log.info("Map:", country);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Document myDoc = Document.parse(response.getBody());
		return myDoc;
	}
	
	public static void main(String[] args) {
//        String test = "[{\"affected_countries\": []}]";
        String test = "  [{\"affected_countries\":[],\"affected_country_division_ids\":[],\"affected_country_divisions\":[],\"affected_country_ids\":[],\"affected_country_region_ids\":[],\"affected_country_regions\":[],\"airline\":null,\"airline_id\":null,\"categories\":[{\"comment\":\"International airports are under the jurisdiction of the federal government. Currently, there are only 13 airports that are open for international flights.\",\"id\":\"e423e3bc-3b03-4f38-be84-06fe02682e75\",\"level\":2,\"type\":0},{\"comment\":\"The Canadian and the United States border closure is extended until 21 June. US citizens, permanent residents and close family members and some special visa holders (such as UN staff and diplomats) are exempt from restrictions on entry into the US. Those allowed to enter the USA may be asked to self-isolate for up to 14 days after arrival.\",\"id\":\"188aebb6-9737-4836-806c-8a9b65974f07\",\"level\":2,\"type\":1},{\"comment\":\"Michigan: Stay at home order extended until 12 June.\\n\\nNew York: Stay at home order extended until 13 June.\\n\\nOregon: State of emergency extended until 6 July.\\n\\nFlorida: The state of emergency was valid until 7 June.\\n\\nCalifornia: All people are being urged to stay at home and maintain social distancing norms.\\n\\nWashington: The stay-at-home order was valid until 31 May, and after that, the restrictions began to lift in four stages.\",\"id\":\"4fe718c9-0439-4c2d-b116-7d2559f4689c\",\"level\":2,\"type\":2},{\"comment\":\"Louisiana: Restaurants have reopened, but each seat should be 3 meters apart with no waiter service.\\n\\nMaine: Residents may attend church services as long as they remain in their cars. Golf courses, hairdressers, and dentists have also been opened.\\n\\nColorado: Barbershops, nail salons, gyms, and daycare centers have been reopened. However, stay-at-home orders are in place in Denver and other surrounding counties.\\n\\nSouth Carolina: Beaches and state parks are open. Only one person or one family will be allowed on Myrtle Beach.\\n\\nTexas: State parks, shopping malls, retail stores, and restaurants have been opened with only 25 per cent capacity in most places.\\n\\nArizona: Restrictions are still in place.\\n\\nNew Mexico: All roads in the city of Gallup have been closed to non-essential traffic. All businesses should be closed by 20:00.\\n\\nGeorgia: Every business in the state has reopened.\\n\\nVermont: Businesses will reopen in a phased approach.\\n\\nMinnesota: Golf courses, public and private marinas, bait shops, and outdoor shooting ranges have opened.\\n\\nNew York, New Jersey, and Connecticut have opened marinas and boatyards for personal use.\\n\\nOhio, Idaho, and North Dakota opened nonessential businesses on 1 May.\\n\\nHawaii: Beaches will be open for exercise.\\n\\nCalifornia - Most beaches are open.\",\"id\":\"cf24a116-78f7-4bf8-8193-cbb3f25804cb\",\"level\":2,\"type\":3},{\"comment\":null,\"id\":\"5c517003-6308-495f-aad9-1a0b716b4a27\",\"level\":-1,\"type\":4}],\"closed\":false,\"comment\":\"Most public health measures and restrictions are under the authority of the individual states. There is a great deal of variability in the measures imposed or lifted by each individual state.\",\"conditions\":[],\"created_at\":\"2020-06-08T05:31:17.645774Z\",\"disease\":{\"common_name\":\"COVID -19\",\"created_at\":\"2020-02-27T21:37:12.120000Z\",\"id\":\"c3d6b5c1-12cb-4834-a696-d74ddec26956\",\"occurs_where\":\"So far (March 2020), this virus has spread from China to 41 other countries. Thes include Afghanistan, Austria, Belgium, Brazil, Cambodia, Croatia, Denmark, Egypt, Estonia, Finland, India, Iraq, Israel, Lebanon, Nepal, Norway, Oman, Philippines, Romania, Russia, Spain, Sri Lanka, Sweden, Switzerland, Canada, Germany, United Arab Emirates, United Kingdom, Australia, Malaysia, Vietnam, Bahrain, France, Kuwait, Taiwan, Thailand, United States of America, Singapore, Iran, Japan, Italy and South Korea.\\r\\n\\r\\nOutbreaks of this disease are well established in China, Japan, South Korea, Iran and Italy. Outbreaks may be beginning in all the other countries.\\r\\n\\r\\n\",\"scientific_name\":\"SARS-COV-2\",\"updated_at\":\"2020-06-13T15:38:21.758000Z\"},\"disease_id\":\"c3d6b5c1-12cb-4834-a696-d74ddec26956\",\"effective_as_of\":\"2020-06-11T20:18:58.629000Z\",\"finish\":null,\"id\":\"f5deb145-e459-421e-ad1f-21d8ae6fe733\",\"origin_country\":{\"banner\":{\"large\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_large_united_states.jpg?v=63732787290\",\"medium\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_medium_united_states.jpg?v=63732787290\",\"original\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_original_united_states.jpg?v=63732787290\",\"small\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_small_united_states.jpg?v=63732787290\"},\"center_lat\":37.09024,\"center_lng\":-95.712891,\"country_code\":\"US\",\"country_code_3\":\"USA\",\"emerg_numbers\":[],\"flag\":{\"list\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_list_US.jpg?v=63758328841\",\"main\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_main_US.jpg?v=63758328841\",\"original\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_original_US.jpg?v=63758328841\"},\"flag_emoji\":\"🇺🇸\",\"geographic_region_id\":\"e5e49071-33ea-4daa-ba57-75986e968d0b\",\"geojson_url\":null,\"id\":\"2373c04e-92ec-45da-a3e0-c68314bc0fcc\",\"name\":\"United States\",\"ne_bound_lat\":71.5388001,\"ne_bound_lng\":-66.885417,\"population\":323127513,\"sec_emer_num\":\"\\r\\n\",\"sw_bound_lat\":18.7763,\"sw_bound_lng\":170.5957,\"topojson_url\":\"https://www.googleapis.com/download/storage/v1/b/sitata-geography-prod/o/countries%2FUS-d9f1060271bc49029da9114a70f225ac.topo.json?generation=1532369786136484&alt=media\",\"travel_status\":1,\"updated_at\":\"2020-06-02T14:54:01.247713Z\"},\"origin_country_id\":\"2373c04e-92ec-45da-a3e0-c68314bc0fcc\",\"references\":[\"https://www.miamiherald.com/news/coronavirus/article243171131.html\",\"https://www.newsbreak.com/news/0OwEMDTQ/gov-brown-extends-state-of-emergency-until-july-6\"],\"start\":\"2020-06-07T05:25:59.596000Z\",\"updated_at\":\"2020-06-08T05:31:17.645774Z\"}]";

		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//			Country[] country = objectMapper.readValue(test, Country[].class);

			List<Map<String, Object>> jsonMap = objectMapper.readValue(test, new TypeReference<List<Map<String, Object>>>() {
					});
			
			log.info("Country size {}....", jsonMap.size());
			log.info("Keys {}....", jsonMap.get(0).keySet().toString());
			log.info("Map {}....", jsonMap.get(0));
//			log.info("closed {}....", country[0].getClosed());
			
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void saveJSONtoDB(Document myDoc, String collectionName) {
		detourRepository.addJSONResponse(myDoc, collectionName);
	}

	private ResponseEntity<String> callAPI(RestTemplate restTemplate, MultiValueMap<String, String> headers,
			String countryUrl) {
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
