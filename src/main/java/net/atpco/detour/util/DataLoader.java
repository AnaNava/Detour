package net.atpco.detour.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import net.atpco.detour.model.AirportInfo;
import net.atpco.detour.model.Ammenities;
import net.atpco.detour.model.CityInfo;
import net.atpco.detour.model.CountryInfo;
import net.atpco.detour.model.Fares;
import net.atpco.detour.model.Flights;
import net.atpco.detour.model.PricingSolution;
import net.atpco.detour.model.UTAInfo;
import net.atpco.detour.repository.DetourRepository;

@Slf4j
public class DataLoader {
	
	private static Map<String, String> countryCodeMap = new HashMap<>();

	public List<PricingSolution> loadPS(String origin, String destination, DetourRepository detourRepository) throws IOException {
		log.info("Loading Shopping Response");
		InputStream shoppingRes = this.getClass().getClassLoader().getResourceAsStream("result" + origin + destination + ".csv");
		List<PricingSolution> pricingSolutions = new ArrayList<>();
		if (shoppingRes == null) return pricingSolutions;
		
		String countryCode = getCountryCode(destination);
		CountryInfo countryInfo = null;
		if (countryCode != null) {
			List<CountryInfo> countryInfos = detourRepository.getCountry(countryCode);
			if (countryInfos.size() > 0) {
				countryInfo = countryInfos.get(0);
			}
			log.info("CountryInfo - " + countryInfo);
		}
		
//		CityInfo destinationCityInfo = null;
//		if (destinationCityInfo != null) {
//			List<CityInfo> cityInfos = detourRepository.getCity(destination);
//			if (cityInfos.size() > 0) {
//				destinationCityInfo = cityInfos.get(0);
//			}
//			log.info("CityInfo - " + destinationCityInfo);
//		}
		
		List<AirportInfo> airportInfoList = null;
		if (airportInfoList != null) {
			List<AirportInfo> airportInfos = detourRepository.getAirport(destination);
			log.info("CityInfo - " + airportInfoList);
		}
	
		try (CSVReader csvReader = new CSVReader(new InputStreamReader(shoppingRes))) {
			csvReader.readNext(); // skip header line
			String[] line;
			int counter = 0;
			while ((line = csvReader.readNext()) != null && counter++ <= 2) {
				String itinStr = line[0];
				String amount = line[1];
				String carrier = line[2];
				String brand = line[3];
				String carryOnAllowance = line[4];
				String checkedBagAllowance = line[5];
				String seatSelection = line[6];
				String cancellation = line[7];
				String advanceChange = line[8];
				String seatPitch = line[9];
				String seatType = line[10];
				String directAisleAccess = line[11];
				String portionShelves = line[12];
				String wifi_quality = line[13];
				String fresh_food_quality = line[14];
				PricingSolution sol = new PricingSolution();
				sol.setCountryCode(countryCode);
				sol.setCountryInfo(countryInfo);
//				sol.setDestinationCityInfo(destinationCityInfo)
				sol.setAirportInfoList(airportInfoList);
				sol.setAmount(amount);
				sol.setNgsRating(portionShelves);

				Ammenities ammenities = new Ammenities();
				ammenities.setDirectAisleAccess(directAisleAccess);
				ammenities.setSeatPitch(seatPitch);
				ammenities.setSeatType(seatType);
				ammenities.setFresh_food_quality(fresh_food_quality);
				ammenities.setWifi_quality(wifi_quality);
				ammenities.setSeatSelection(seatSelection);

				Flights flight = new Flights();
				flight.setAmmenities(ammenities);
				List<Flights> flights = new ArrayList<>();
				flights.add(flight);

				UTAInfo utaInfo = new UTAInfo();
				utaInfo.setAdvanceChange(advanceChange);
				utaInfo.setCancellation(cancellation);
				utaInfo.setCarryOnAllowance(carryOnAllowance);
				utaInfo.setCheckedBagAllowance(checkedBagAllowance);

				Fares fare = new Fares();
				fare.setBrand(brand);
				fare.setUtaInfo(utaInfo);

				List<Fares> fares = new ArrayList<>();
				fares.add(fare);

				sol.setFlightsList(flights);
				sol.setFares(fares);

				PricingSolution sol1 = parseItinStr(itinStr, sol);
				if (sol1 != null) {
					pricingSolutions.add(sol);
				}
			}
			shoppingRes.close();
		}

		log.info("Loaded Shopping Response {}", pricingSolutions.size());
		return pricingSolutions;
	}

	private PricingSolution parseItinStr(String engineItinStr, PricingSolution sol) {
		String[] farecomponents = engineItinStr.split("\\.");

		log.debug("Lengthfcs=" + farecomponents.length);
		log.debug("Itinerary" + engineItinStr);
		PricingSolution sol1 = null;
		if (farecomponents.length == 1) { // one iteration only
			sol1 = parseFCStr(engineItinStr, sol);
			log.debug("fareComponent string '{}'", sol1);
		}
		return sol1;
	}

	private PricingSolution parseFCStr(String fcStr, PricingSolution sol) {
		String[] partsWithFbc = fcStr.split("-");

		String[] fareInfoParts = partsWithFbc[1].split(" ");
		String carrier = fareInfoParts[0]; // carrier
		String fbc = fareInfoParts[1]; // fare basis code
		sol.getFares().get(0).setFareClass(fbc);
		sol.setCarrier(carrier);

		String justFcStr = partsWithFbc[0];

		String[] faresegments = justFcStr.split("~");
		if (faresegments.length >= 1) {
			String[] parts = faresegments[0].split("/");
			sol.setOrigin(parts[0]);
			sol.setDestination(faresegments[faresegments.length-1].split("/")[6]);
			sol.getFlightsList().get(0).setCabin(String.valueOf(parts[4].charAt(0)));
			sol.getFlightsList().get(0).setFlightNumber(parts[2]);
			sol.setDepartureDate(parts[1]);
		} else {
			return null;
		}
		return sol;

	}

	public Map<String, List<String>> loadCityPair() throws IOException {
		log.info("Loading Shopping Response");
		InputStream shoppingRes = this.getClass().getClassLoader().getResourceAsStream("cityPair.csv");
		Map<String, List<String>> pairs = new HashMap<>();
		try (CSVReader csvReader = new CSVReader(new InputStreamReader(shoppingRes))) {
			csvReader.readNext(); // skip header line
			String[] line;
			while ((line = csvReader.readNext()) != null) {
				String origin = line[0];
				String detination = line[1];
				String destCountry = line[2];
				List<String> cities = pairs.get(origin);
				if (cities != null) {
					cities.add(detination);
				} else {
					cities = new ArrayList<String>();
					cities.add(detination);
					pairs.put(origin, cities);
				}
				
				if (countryCodeMap.get(detination) == null ) {
					countryCodeMap.put(detination, destCountry);
				}

			}
			shoppingRes.close();
		}

		log.info("Loaded Shopping Response {}", pairs.size());
		return pairs;
	}
	
	
	public static String getCountryCode(String city) {
		return countryCodeMap.get(city);
	}

}
