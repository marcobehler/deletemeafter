package com.jetbrains.thenextfacebook.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ThenextfacebookApplicationTest {

	private Logger logger = LoggerFactory.getLogger(ThenextfacebookApplicationTest.class);

	@Autowired
	private MockMvc mockMvc;

	@Test
	void registrationWorksThroughAllLayers() throws Exception {
		logger.info("");
		Thread.sleep(10000);

		mockMvc.perform(get("/")
				.contentType("text/html"))
				.andExpect(status().isOk());

	}


}
