package com.capgemini.chess.service;
import java.util.List;

import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.StatsTO;


public interface UserService {
	
	
	/*
	 * Prepares a list of maximum 5 possible challenges. Primarily the list 
	 * includes challenges called on the user. Then the list is filled with 
	 * suggestions of other possible challenges.
	 */
	public List<ChallengeTO> suggestFivePossibleChallenges(Long myselfId) 
			throws NoSuchUserException;
	
	
	/*
	 * Creates a challenge for the specified calling user and defending user.
	 * If the contrary challenge already exists - then the challenge
	 * is automatically accepted.
	 */
	public ChallengeTO setupChallenge(Long defendingId, Long callingId) 
			throws NoSuchUserException;
	
	
	/*
	 * Brings statistics for the array of given users.
	 */
	public List<StatsTO> showUserStats(Long[] userIds) 
			throws NoSuchUserException;
	
}
