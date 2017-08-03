package com.capgemini.chess.controller.rest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import com.capgemini.chess.ChessApplication;
import com.capgemini.chess.DatabaseMockupObject;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseMockupObject.class, ChessApplication.class})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@WebAppConfiguration
public class UserRestServiceTest {

    @Autowired
    private UserRestService userRestService;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(userRestService).build();
	}

	
	@Test
	public void shouldChallengeGEThaveProperSize() throws Exception {

		// when
		ResultActions response = mockMvc.perform(get("/challenge").param("id", "7"));
		
		// then
		response.andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(5)));
	}
	
	
	@Test
	public void shouldChallengeGEThaveProperContent() throws Exception {

		// when
		ResultActions response = mockMvc.perform(get("/challenge").param("id", "7"));
		
		// then
		response.andExpect(status().isOk())
		        .andExpect(jsonPath("$[0].callingUser", is(1)))
		        .andExpect(jsonPath("$[0].defendingUser", is(7)))
		        .andExpect(jsonPath("$[0].status", is("THROWN")));
	}
	
	
	@Test
	public void shouldChallengePOSThaveProperSize() throws Exception {

		// when
		ResultActions response = mockMvc.perform(post("/challenge")
				.param("defId", "1").param("callId", "2"));
		
		// then
		response.andExpect(status().isOk())
		        .andExpect(jsonPath("$").exists());
	}
	
	
	@Test
	public void shouldChallengePOSThaveProperContent() throws Exception {

		// when
		ResultActions response = mockMvc.perform(post("/challenge")
				.param("defId", "1").param("callId", "9"));
		
		// then
		response.andExpect(status().isOk())
		        .andExpect(jsonPath("$.callingUser", is(1)))
		        .andExpect(jsonPath("$.defendingUser", is(9)))
		        .andExpect(jsonPath("$.status", is("ACCEPTED")));
	}
	
	
	@Test
	public void shouldStatsGEThaveProperSize() throws Exception {
		
		// when
		ResultActions response = mockMvc.perform(get("/stats")
				.param("userId", "1").param("userId", "9").param("userId", "2"));
		
		// then
		response.andExpect(status().isOk())
		        .andExpect(jsonPath("$[0].id", is(1)))
		        .andExpect(jsonPath("$[1].id", is(9)))
		        .andExpect(jsonPath("$[2].id", is(2)));
	}
	
	
	@Test(expected = NestedServletException.class)
	public void shouldChallengeGETthrowException() throws Exception {
		
		// when
		ResultActions response = mockMvc.perform(get("/challenge").param("id", "6"));
		
		// then
		response.andExpect(status().is5xxServerError());
	}
	
	
	@Test(expected = NestedServletException.class)
	public void shouldChallengePOSTthrowException() throws Exception {
		
		// when
		ResultActions response = mockMvc.perform(post("/challenge")
				.param("defId", "6").param("callId", "7"));
		
		// then
		response.andExpect(status().is5xxServerError());
	}
	
	
	@Test(expected = NestedServletException.class)
	public void shouldStatsGETthrowException() throws Exception {
		
		// when
		ResultActions response = mockMvc.perform(get("/stats").param("userId", "6"));
		
		// then
		response.andExpect(status().is5xxServerError());
	}
	
	
	@Test
	public void shouldStatsGETtellClientError() throws Exception {
		
		// when
		ResultActions response = mockMvc.perform(get("/stats").param("fakeparam", "1"));
		
		// then
		response.andExpect(status().is4xxClientError());
	}
	
}

