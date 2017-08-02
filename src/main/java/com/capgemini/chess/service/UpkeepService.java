package com.capgemini.chess.service;
import java.util.List;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.to.ChallengeTO;


public interface UpkeepService {
	
	
	/*
	 * Returns a list of challenges with specified status.
	 */
	public List<ChallengeTO> getChallengesByStatus(ChallengeStatus challengeStatus) 
			throws NoSuchChallException;
	
	
	/*
	 * Deletes challenges of IDs contained on the list.
	 */
	public void deleteChallenge(ChallengeTO challenge) 
			throws NoSuchChallException;
	
}
