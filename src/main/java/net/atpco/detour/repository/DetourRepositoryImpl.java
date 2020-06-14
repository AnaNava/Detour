package net.atpco.detour.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.atpco.detour.model.AirportInfo;
import net.atpco.detour.model.CityInfo;
import net.atpco.detour.model.CountryInfo;


@Repository
@AllArgsConstructor
@Slf4j
public class DetourRepositoryImpl implements DetourRepository {
    
	@Autowired
    private MongoTemplate template;


	@Override
	public List<CountryInfo> getCountry(String countryCode) {
		Query query = new Query().addCriteria(Criteria.where("countryCode").is(countryCode));
        return template.find(query, CountryInfo.class, "CountryInfo");
	}

	@Override
	public List<CityInfo> getCity(String cityCode) {
		Query query = new Query().addCriteria(Criteria.where("cityyCode").is(cityCode));
        return template.find(query, CityInfo.class, "CityInfo");
	}

	@Override
	public List<AirportInfo> getAirport(String airportCode) {
		Query query = new Query().addCriteria(Criteria.where("airportCode").is(airportCode));
        return template.find(query, AirportInfo.class, "AirportInfo");
	}
	

	@Override
	public void addJSONResponse(Document myDoc, String collectionName) {
		log.info("Adding JSON as document");	
		template.save(myDoc, collectionName);
	}
  
}
