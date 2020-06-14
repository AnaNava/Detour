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
import net.atpco.detour.model.DetourRequest;
import net.atpco.detour.repository.DetourRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

	private DetourRepository detourRepository;

	private final String countryUrl = "https://www.sitata.com/api/v2/countries/countryid/travel_restrictions";
	private final String shoppingUrl = "";

	public void search() {
		// Call shopping engine API

		// Call Retailing API for amenities, uta and upas

	}

	public HttpEntity<String> getShoppingResponse(DetourRequest detourReq, String destination) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");

		log.info("Sending shopping request to {} ", shoppingUrl);

		ResponseEntity<String> response = callShoppingAPI(restTemplate, headers, shoppingUrl, detourReq, destination);

		Document myDoc = parseJSON(response);
		myDoc.append("COUNTRY_CODE", detourReq);

		// saveJSONtoDB(myDoc, "COUNTRY_RES");

		return response;
		
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

		ResponseEntity<String> response = callCountryAPI(restTemplate, headers, url);

		log.info("API Response {} ", response.getBody());

		Document myDoc = parseJSON(response);
//		myDoc.append("COUNTRY_CODE", countryCode);

		// saveJSONtoDB(myDoc, "COUNTRY_RES");

		return response;
	}

	private Document parseJSON(ResponseEntity<String> response) {

		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//			Country[] country = objectMapper.readValue(response.getBody(), Country[].class);
			
			List<Map<String, Object>> jsonMap = objectMapper.readValue(response.getBody(), new TypeReference<List<Map<String, Object>>>() {
			});

			log.info("Map:", jsonMap);
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

//		Document myDoc = Document.parse(response.getBody());
		return null;
	}
	
	public static void main(String[] args) {
//        String test = "[{\"affected_countries\": []}]";
//        String test = "  [{\"affected_countries\":[],\"affected_country_division_ids\":[],\"affected_country_divisions\":[],\"affected_country_ids\":[],\"affected_country_region_ids\":[],\"affected_country_regions\":[],\"airline\":null,\"airline_id\":null,\"categories\":[{\"comment\":\"International airports are under the jurisdiction of the federal government. Currently, there are only 13 airports that are open for international flights.\",\"id\":\"e423e3bc-3b03-4f38-be84-06fe02682e75\",\"level\":2,\"type\":0},{\"comment\":\"The Canadian and the United States border closure is extended until 21 June. US citizens, permanent residents and close family members and some special visa holders (such as UN staff and diplomats) are exempt from restrictions on entry into the US. Those allowed to enter the USA may be asked to self-isolate for up to 14 days after arrival.\",\"id\":\"188aebb6-9737-4836-806c-8a9b65974f07\",\"level\":2,\"type\":1},{\"comment\":\"Michigan: Stay at home order extended until 12 June.\\n\\nNew York: Stay at home order extended until 13 June.\\n\\nOregon: State of emergency extended until 6 July.\\n\\nFlorida: The state of emergency was valid until 7 June.\\n\\nCalifornia: All people are being urged to stay at home and maintain social distancing norms.\\n\\nWashington: The stay-at-home order was valid until 31 May, and after that, the restrictions began to lift in four stages.\",\"id\":\"4fe718c9-0439-4c2d-b116-7d2559f4689c\",\"level\":2,\"type\":2},{\"comment\":\"Louisiana: Restaurants have reopened, but each seat should be 3 meters apart with no waiter service.\\n\\nMaine: Residents may attend church services as long as they remain in their cars. Golf courses, hairdressers, and dentists have also been opened.\\n\\nColorado: Barbershops, nail salons, gyms, and daycare centers have been reopened. However, stay-at-home orders are in place in Denver and other surrounding counties.\\n\\nSouth Carolina: Beaches and state parks are open. Only one person or one family will be allowed on Myrtle Beach.\\n\\nTexas: State parks, shopping malls, retail stores, and restaurants have been opened with only 25 per cent capacity in most places.\\n\\nArizona: Restrictions are still in place.\\n\\nNew Mexico: All roads in the city of Gallup have been closed to non-essential traffic. All businesses should be closed by 20:00.\\n\\nGeorgia: Every business in the state has reopened.\\n\\nVermont: Businesses will reopen in a phased approach.\\n\\nMinnesota: Golf courses, public and private marinas, bait shops, and outdoor shooting ranges have opened.\\n\\nNew York, New Jersey, and Connecticut have opened marinas and boatyards for personal use.\\n\\nOhio, Idaho, and North Dakota opened nonessential businesses on 1 May.\\n\\nHawaii: Beaches will be open for exercise.\\n\\nCalifornia - Most beaches are open.\",\"id\":\"cf24a116-78f7-4bf8-8193-cbb3f25804cb\",\"level\":2,\"type\":3},{\"comment\":null,\"id\":\"5c517003-6308-495f-aad9-1a0b716b4a27\",\"level\":-1,\"type\":4}],\"closed\":false,\"comment\":\"Most public health measures and restrictions are under the authority of the individual states. There is a great deal of variability in the measures imposed or lifted by each individual state.\",\"conditions\":[],\"created_at\":\"2020-06-08T05:31:17.645774Z\",\"disease\":{\"common_name\":\"COVID -19\",\"created_at\":\"2020-02-27T21:37:12.120000Z\",\"id\":\"c3d6b5c1-12cb-4834-a696-d74ddec26956\",\"occurs_where\":\"So far (March 2020), this virus has spread from China to 41 other countries. Thes include Afghanistan, Austria, Belgium, Brazil, Cambodia, Croatia, Denmark, Egypt, Estonia, Finland, India, Iraq, Israel, Lebanon, Nepal, Norway, Oman, Philippines, Romania, Russia, Spain, Sri Lanka, Sweden, Switzerland, Canada, Germany, United Arab Emirates, United Kingdom, Australia, Malaysia, Vietnam, Bahrain, France, Kuwait, Taiwan, Thailand, United States of America, Singapore, Iran, Japan, Italy and South Korea.\\r\\n\\r\\nOutbreaks of this disease are well established in China, Japan, South Korea, Iran and Italy. Outbreaks may be beginning in all the other countries.\\r\\n\\r\\n\",\"scientific_name\":\"SARS-COV-2\",\"updated_at\":\"2020-06-13T15:38:21.758000Z\"},\"disease_id\":\"c3d6b5c1-12cb-4834-a696-d74ddec26956\",\"effective_as_of\":\"2020-06-11T20:18:58.629000Z\",\"finish\":null,\"id\":\"f5deb145-e459-421e-ad1f-21d8ae6fe733\",\"origin_country\":{\"banner\":{\"large\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_large_united_states.jpg?v=63732787290\",\"medium\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_medium_united_states.jpg?v=63732787290\",\"original\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_original_united_states.jpg?v=63732787290\",\"small\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_small_united_states.jpg?v=63732787290\"},\"center_lat\":37.09024,\"center_lng\":-95.712891,\"country_code\":\"US\",\"country_code_3\":\"USA\",\"emerg_numbers\":[],\"flag\":{\"list\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_list_US.jpg?v=63758328841\",\"main\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_main_US.jpg?v=63758328841\",\"original\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/US/2373c04e-92ec-45da-a3e0-c68314bc0fcc_original_US.jpg?v=63758328841\"},\"flag_emoji\":\"🇺🇸\",\"geographic_region_id\":\"e5e49071-33ea-4daa-ba57-75986e968d0b\",\"geojson_url\":null,\"id\":\"2373c04e-92ec-45da-a3e0-c68314bc0fcc\",\"name\":\"United States\",\"ne_bound_lat\":71.5388001,\"ne_bound_lng\":-66.885417,\"population\":323127513,\"sec_emer_num\":\"\\r\\n\",\"sw_bound_lat\":18.7763,\"sw_bound_lng\":170.5957,\"topojson_url\":\"https://www.googleapis.com/download/storage/v1/b/sitata-geography-prod/o/countries%2FUS-d9f1060271bc49029da9114a70f225ac.topo.json?generation=1532369786136484&alt=media\",\"travel_status\":1,\"updated_at\":\"2020-06-02T14:54:01.247713Z\"},\"origin_country_id\":\"2373c04e-92ec-45da-a3e0-c68314bc0fcc\",\"references\":[\"https://www.miamiherald.com/news/coronavirus/article243171131.html\",\"https://www.newsbreak.com/news/0OwEMDTQ/gov-brown-extends-state-of-emergency-until-july-6\"],\"start\":\"2020-06-07T05:25:59.596000Z\",\"updated_at\":\"2020-06-08T05:31:17.645774Z\"}]";

		String test = "[{\"affected_countries\":[],\"affected_country_division_ids\":[],\"affected_country_divisions\":[],\"affected_country_ids\":[],\"affected_country_region_ids\":[],\"affected_country_regions\":[],\"airline\":null,\"airline_id\":null,\"categories\":[{\"comment\":\"* Most airlines have reduced flight services or suspended some routes due to the decrease in demand.\\n\\n* As of 27 May, passengers are required to wear masks on all flights from Korea.\",\"id\":\"06742db8-1bcc-4ad7-b973-cc1cbac0a5ae\",\"level\":2,\"type\":0},{\"comment\":\"Foreign nationals who have been in Hubei province in China within the previous 14 days are not permitted to enter South Korea.\\n\\nThose travellers who are transiting through South Korea will receive a temperature check on arrival in Korea. Anyone with symptoms will be subject to a COVID-19 test and hospitalization if found positive. Passengers who do not show symptoms and/or test negative may continue their travels.\\n\\nFrom 1 June 2020, foreign nationals residing in South Korea on most types of long-term visaa will need to apply for a re-entry permit at a local immigration office or at the airport before undertaking any travel out of South Korea. Those travelling with a re-entry permit will also be required to have a medical examination no earlier than 48 hours before they plan to return to South Korea and to obtain a medical certificate in English or Korean to present to the Korean authorities on their arrival.\\n\\nEvery person arriving in South Korea from abroad is subject to a 14-day self-isolation period. For those with no symptoms on arrival, Korean nationals and long term foreign visitors with an Alien Registration Card, and Korean residence are allowed to self-quarantine at home. Arrivals from the USA and Europe must receive a test within three days. Arrivals from other countries must receive a test within 14 days.\\n\\nPeople who do not have any local residence in South Korea will be isolated at a government quarantine facility, which will cost about ?100,000 ($81.20) per day. Anyone coming from abroad will be tested for COVID-19, and if symptoms are detected or if tested positive, they will be hospitalized or sent to an isolation facility immediately.\\n\\nCruise ships are not being allowed to dock at the ports, including Jeju.\",\"id\":\"3fc524a6-2ebe-4aed-a05b-83ac04b05fc3\",\"level\":2,\"type\":1},{\"comment\":\"* The Public Health Emergency has been extended until 20 August.\\n\\n* Authorities have extended the existing restrictions in the Seoul metropolitan area, which includes Gyeonggi and Incheon provinces, until at least 14 June.\",\"id\":\"a91bfa33-89e8-413e-b9a7-7884b5abc20d\",\"level\":2,\"type\":2},{\"comment\":\"Some social distancing measures were lifted in May. However, an increase in COVID-19 cases led authorities to reimpose some restrictions.\\n\\nMuseums, parks and art galleries in Seoul will close again until 14 June. Outside Seoul, limited gatherings, and reopening of public sites such as museums and indoor sports facilities are being allowed. Religious activities, national parks, and other outdoor public areas are also allowed to open. However, citizens are asked to avoid social gatherings or going to crowded places, including restaurants and bars.\\n\\n* Businesses and companies have resumed operations but are now encouraged to re-introduce flexible working hours and work from home.\\n\\n* Bars, karaoke rooms and other entertainment venues are closed.\\n\\n* Masks and staying two meters away from others are mandatory in public places\\n\\n* Mass gatherings remain banned in Seoul Plaza, Gwanghwamun Square, Cheonggye Plaza, and Daegu.\\n\\n* Schools and other educational institutions opened as of 20 May.\",\"id\":\"5d22b6ed-dd81-4de5-b89a-7d374bd10b50\",\"level\":2,\"type\":3},{\"comment\":\"Public transportation (buses, trains, and taxis) are available in Korea for inter-city travel and to reach the airport. As of 27 May, face masks are mandatory on Korean public transport.?-\",\"id\":\"37bf10d6-5f11-4d70-8267-1869087aec4e\",\"level\":2,\"type\":4}],\"closed\":false,\"comment\":null,\"conditions\":[],\"created_at\":\"2020-06-08T15:49:10.357658Z\",\"disease\":{\"common_name\":\"COVID -19\",\"created_at\":\"2020-02-27T21:37:12.120000Z\",\"id\":\"c3d6b5c1-12cb-4834-a696-d74ddec26956\",\"occurs_where\":\"So far (March 2020), this virus has spread from China to 41 other countries. Thes include Afghanistan, Austria, Belgium, Brazil, Cambodia, Croatia, Denmark, Egypt, Estonia, Finland, India, Iraq, Israel, Lebanon, Nepal, Norway, Oman, Philippines, Romania, Russia, Spain, Sri Lanka, Sweden, Switzerland, Canada, Germany, United Arab Emirates, United Kingdom, Australia, Malaysia, Vietnam, Bahrain, France, Kuwait, Taiwan, Thailand, United States of America, Singapore, Iran, Japan, Italy and South Korea.\\r\\n\\r\\nOutbreaks of this disease are well established in China, Japan, South Korea, Iran and Italy. Outbreaks may be beginning in all the other countries.\\r\\n\\r\\n\",\"scientific_name\":\"SARS-COV-2\",\"updated_at\":\"2020-06-14T15:39:13.581000Z\"},\"disease_id\":\"c3d6b5c1-12cb-4834-a696-d74ddec26956\",\"effective_as_of\":\"2020-06-12T20:35:28.496000Z\",\"finish\":null,\"id\":\"2f4c8cbe-2562-469d-82ee-0bf6f78fdb19\",\"origin_country\":{\"banner\":{\"large\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/KR/0eb1c84a-abca-4cd3-9699-3c6b1a3315fe_large_south_korea.jpg?v=63732782259\",\"medium\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/KR/0eb1c84a-abca-4cd3-9699-3c6b1a3315fe_medium_south_korea.jpg?v=63732782259\",\"original\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/KR/0eb1c84a-abca-4cd3-9699-3c6b1a3315fe_original_south_korea.jpg?v=63732782259\",\"small\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/KR/0eb1c84a-abca-4cd3-9699-3c6b1a3315fe_small_south_korea.jpg?v=63732782259\"},\"center_lat\":35.907757,\"center_lng\":127.766922,\"country_code\":\"KR\",\"country_code_3\":\"KOR\",\"emerg_numbers\":[],\"flag\":{\"list\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/KR/0eb1c84a-abca-4cd3-9699-3c6b1a3315fe_list_KR.jpg?v=63699588987\",\"main\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/KR/0eb1c84a-abca-4cd3-9699-3c6b1a3315fe_main_KR.jpg?v=63699588987\",\"original\":\"https://storage.googleapis.com/sitata-uploads/uploads/countries/KR/0eb1c84a-abca-4cd3-9699-3c6b1a3315fe_original_KR.jpg?v=63699588987\"},\"flag_emoji\":\"??\",\"geographic_region_id\":\"a0df13ac-24cc-4007-a2ee-ca82266a6405\",\"geojson_url\":null,\"id\":\"0eb1c84a-abca-4cd3-9699-3c6b1a3315fe\",\"name\":\"South Korea\",\"ne_bound_lat\":38.63400000000001,\"ne_bound_lng\":131.1603,\"population\":51269185,\"sec_emer_num\":\"\\r\\n\",\"sw_bound_lat\":33.0041,\"sw_bound_lng\":124.5863,\"topojson_url\":\"https://www.googleapis.com/download/storage/v1/b/sitata-geography-prod/o/countries%2FKR-2146d5ffcf3a475cb45c70f87a5b3093.topo.json?generation=1532369787807982&alt=media\",\"travel_status\":0,\"updated_at\":\"2020-04-02T13:19:30.773314Z\"},\"origin_country_id\":\"0eb1c84a-abca-4cd3-9699-3c6b1a3315fe\",\"references\":[\"https://www.iatatravelcentre.com/international-travel-document-news/1580226297.htm\",\"https://www.straitstimes.com/asia/east-asia/south-korea-reports-79-new-coronavirus-cases-most-since-april-5\"],\"start\":\"2020-06-07T15:34:53.893000Z\",\"updated_at\":\"2020-06-08T15:49:10.357658Z\"}]";
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

	private ResponseEntity<String> callShoppingAPI(RestTemplate restTemplate, MultiValueMap<String, String> headers,
			String url, DetourRequest detourReq, String destination) {
		
		String body = getShoppingRequestBody(detourReq, destination);
		HttpEntity<String> request = new HttpEntity<>("", headers);
		ResponseEntity<String> response = null;
		try {
			log.info("Sending country request for  {} ", url);

			response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

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
	
	
	private String getShoppingRequestBody(DetourRequest detourReq, String destination) {
		// TODO Auto-generated method stub
		return null;
	}

	private ResponseEntity<String> callCountryAPI(RestTemplate restTemplate, MultiValueMap<String, String> headers,
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
