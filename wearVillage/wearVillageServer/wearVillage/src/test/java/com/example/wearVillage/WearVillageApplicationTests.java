package com.example.wearVillage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootTest
class WearVillageApplicationTests {

	@Test
	void contextLoads() {
	}
		@MockBean
		private ServerEndpointExporter serverEndpointExporter;


}
