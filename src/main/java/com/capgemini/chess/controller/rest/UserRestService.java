package com.capgemini.chess.controller.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.chess.service.NoSuchUserException;
import com.capgemini.chess.service.UserService;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.StatsTO;


@Controller
@ResponseBody
public class UserRestService {

	@Autowired
	UserService userService;

	
	@RequestMapping(value = "/challenge", method = RequestMethod.GET)
	public List<ChallengeTO> getChallengeSuggestions(@RequestParam ("id") Long id) throws NoSuchUserException {
		return userService.suggestFivePossibleChallenges(id);
	}
	
	@RequestMapping(value = "/challenge", method = RequestMethod.POST)
	public ChallengeTO postSetupChallenge(@RequestParam ("defId") Long defId, 
			                         @RequestParam ("callId") Long callId) throws NoSuchUserException {
		return userService.setupChallenge(defId, callId);
	}
	
	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public List<StatsTO> getUserStats(@RequestParam ("userId") Long[] userIds) throws NoSuchUserException{
		return userService.showUserStats(userIds);
	}
	
}
