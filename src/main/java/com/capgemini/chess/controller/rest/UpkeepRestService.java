package com.capgemini.chess.controller.rest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.NoSuchChallException;
import com.capgemini.chess.service.UpkeepService;
import com.capgemini.chess.service.to.ChallengeTO;

@Controller
@ResponseBody
@RequestMapping("/upkeep")
public class UpkeepRestService {

	@Autowired
	UpkeepService upkeepService;
	
	
	@RequestMapping(value = "/challenge", method = RequestMethod.GET)
	public List<ChallengeTO> getChallengesByStatus(@RequestParam ("status") 
	        String status) throws NoSuchChallException {
		ChallengeStatus challengestatus = ChallengeStatus.valueOf(status.toUpperCase());
		return upkeepService.getChallengesByStatus(challengestatus);
	}
	
	@RequestMapping(value = "/challenge", method = RequestMethod.DELETE)
	public void deleteChallenge(@RequestBody ChallengeTO challenge) 
			throws NoSuchChallException{
		upkeepService.deleteChallenge(challenge);
	}
	
}
