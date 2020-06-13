package net.atpco.detour.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.atpco.detour.model.DetourRequest;


@Repository
@AllArgsConstructor
@Slf4j
public class DetourRepositoryImpl implements DetourRepository {
    @Autowired
    private MongoTemplate template;

	@Override
	public DetourRequest insert(DetourRequest detourReq, String collectionName) {
		
		return (DetourRequest) template.insert(detourReq, collectionName);
	}

	@Override
	public void addJSONResponse(Document myDoc, String collectionName) {
		log.info("Adding JSON as document");
		
		template.save(myDoc, collectionName);
		
	}
  
}
