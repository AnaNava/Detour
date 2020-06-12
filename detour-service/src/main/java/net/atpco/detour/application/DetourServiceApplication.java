package net.atpco.detour.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({ DetourConfiguration.class })
@EnableAutoConfiguration(exclude={RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class,MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class,
		JmxAutoConfiguration.class, SpringApplicationAdminJmxAutoConfiguration.class, 
		IntegrationAutoConfiguration.class})
@PropertySource(ignoreResourceNotFound = true, value = { "classpath:detour.properties" })
public class DetourServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetourServiceApplication.class, args);
	}

}
