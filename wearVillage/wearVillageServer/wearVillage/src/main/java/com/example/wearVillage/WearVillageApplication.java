package com.example.wearVillage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class WearVillageApplication {

	public static void main(String[] args) {
		SpringApplication.run(WearVillageApplication.class, args);
	}

	
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}


	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}


}
