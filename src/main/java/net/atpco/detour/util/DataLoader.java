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
import net.atpco.detour.model.Ammenities;
import net.atpco.detour.model.CountryInfo;
import net.atpco.detour.model.Fares;
import net.atpco.detour.model.Flights;
import net.atpco.detour.model.PricingSolution;
import net.atpco.detour.model.UTAInfo;
import net.atpco.detour.repository.DetourRepository;

@Slf4j
public class DataLoader {
	
	private static Map<String, String> countryCodeMap = new HashMap<>();

	public List<PricingSolution> loadPS(String fileName, DetourRepository detourRepository) throws IOException {
		log.info("Loading Shopping Response");
		InputStream shoppingRes = this.getClass().getClassLoader().getResourceAsStream("result" + fileName + ".csv");
		List<PricingSolution> pricingSolutions = new ArrayList<>();
		try (CSVReader csvReader = new CSVReader(new InputStreamReader(shoppingRes))) {
			csvReader.readNext(); // skip header line
			String[] line;
			while ((line = csvReader.readNext()) != null) {
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
				sol.setAmountWithCurrency(amount);
				sol.setNgsRating(portionShelves);

				Ammenities ammenities = new Ammenities();
				ammenities.setDirectAisleAccess(directAisleAccess);
				ammenities.setSeatPitch(seatPitch);
				ammenities.setSeatType(seatType);
				ammenities.setFresh_food_quality(fresh_food_quality);
				ammenities.setWifi_quality(wifi_quality);

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
				
				setCountryInfo(sol, detourRepository);
				pricingSolutions.add(sol);

			}
			shoppingRes.close();
		}

		log.info("Loaded Shopping Response {}", pricingSolutions.size() );
		return pricingSolutions;
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
				List<String> cities = pairs.get(origin);
				if (cities != null) {
					cities.add(detination);
				} else {
					cities = new ArrayList<String>();
					cities.add(detination);
					pairs.put(origin, cities);
				}

			}
			shoppingRes.close();
		}

		log.info("Loaded Shopping Response {}", pairs.size() );
		return pairs;
	}
	

	private void setCountryInfo(PricingSolution sol, DetourRepository detourRepository) {
		List<CountryInfo> countryInfo = detourRepository.getCountry(getCountryCode(sol.getDestination()));
		sol.setCountryInfo(countryInfo.get(0));
	}


	public static String getCountryCode(String city) {
		return countryCodeMap.get(city);
	}

}