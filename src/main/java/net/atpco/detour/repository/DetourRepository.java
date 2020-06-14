package net.atpco.detour.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import net.atpco.detour.model.AirportInfo;
import net.atpco.detour.model.CityInfo;
import net.atpco.detour.model.CountryInfo;

@Repository
public interface DetourRepository {

	List<CountryInfo> getCountry(String countryCode);
	List<CityInfo> getCity(String cityCode);
	List<AirportInfo> getAirport(String airportCode);
	void addJSONResponse(Document myDoc, String collectionName);
}
