package net.atpco.detour.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@EnableMongoRepositories
@Configuration
public class MongoConfig {
	
	@Value("${spring.data.mongodb.host}")
	private String mongoHost;
	
	@Value("${spring.data.mongodb.port}")
	private String mongoPort;
	
	@Value("${spring.data.mongodb.database}")
	private String mongoDatabase;
	
	@Value("${spring.data.mongodb.username}")
	private String mongoUser;
	
	@Value("${spring.data.mongodb.password}")
	private String mongoPassword;
	
	@Bean
	public MongoClient mongoClient() {
	    return MongoClients.create(getMongoUri());
	}

	@Bean
	public MongoDbFactory mongoDbFactory() {
		return new SimpleMongoDbFactory(new MongoClientURI(getMongoUri()));
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoDbFactory());
	}

    
    private String getMongoUri() {
   
        String template = "mongodb+srv://%s:%s@%s/%s?retryWrites=true&w=majority";
    	//String template = "mongodb://%s:%s@%s:%s/%s?replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false";
    	String connectionString = String.format(template, mongoUser, mongoPassword, mongoHost, mongoDatabase);
        System.out.println("******  " + connectionString);
        return connectionString;
    }
}
