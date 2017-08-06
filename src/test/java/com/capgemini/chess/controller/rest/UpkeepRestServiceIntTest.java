package com.capgemini.chess.controller.rest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.chess.ChessApplication;
import com.capgemini.chess.DatabaseMockupObject;
import com.capgemini.chess.service.to.ChallengeTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseMockupObject.class, ChessApplication.class})
@WebAppConfiguration
@DirtiesContext
public class UpkeepRestServiceIntTest {

    @Autowired
    private UpkeepRestService upkeepRestService;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(upkeepRestService).build();
	}

	
	@Test
	public void shouldChallengeGEThaveProperSize() throws Exception {

		// when
		ResultActions response = mockMvc.perform(get("/upkeep/challenge")
				.param("status", "thrown"));
		
		// then
		response.andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(4)));
	}
	
	
	@Test
	public void shouldChallengeGEThaveProperContent() throws Exception {

		// when
		ResultActions response = mockMvc.perform(get("/upkeep/challenge")
				.param("status", "thrown"));
		
		// then
		response.andExpect(status().isOk())
		        .andExpect(jsonPath("$[0].callingUser").exists())
		        .andExpect(jsonPath("$[0].defendingUser").exists())
		        .andExpect(jsonPath("$[0].status").exists());
	}
	
	
	@Test
	public void shouldChallengeDELETEhaveProperStatusAndEffect() throws Exception {

		// given
		int initialChallengesNumber = upkeepRestService.getChallengesByStatus("THROWN").size();
		ChallengeTO exampleChallenge = upkeepRestService.getChallengesByStatus("THROWN").get(0);
		String jsonChallenge = new ObjectMapper().writeValueAsString(exampleChallenge);

		// when
		ResultActions response = mockMvc.perform(delete("/upkeep/challenge")
				.contentType(MediaType.APPLICATION_JSON).content(jsonChallenge));
		
		// then
		int finalChallengesNumber = upkeepRestService.getChallengesByStatus("THROWN").size();
		assertEquals(initialChallengesNumber-1, finalChallengesNumber);
		response.andExpect(status().isOk());
	}
	
}

