package net.atpco.detour.repository;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import net.atpco.detour.model.DetourRequest;

@Repository
public interface DetourRepository {
	DetourRequest insert(DetourRequest detourReq, String collectionName);
//	void dropCollection(String collectionName);
//	List<UniqueMarket> getUniqueMarkets(String queryId);
	

	void addJSONResponse(Document myDoc, String collectionName);
}
