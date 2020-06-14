package net.atpco.detour.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import net.atpco.detour.model.CountryInfo;
import net.atpco.detour.model.DetourRequest;

@Repository
public interface DetourRepository {
	
	DetourRequest insert(DetourRequest detourReq, String collectionName);
//	void dropCollection(String collectionName);
	List<CountryInfo> getCountry(String countryCode);
	

	void addJSONResponse(Document myDoc, String collectionName);
}
