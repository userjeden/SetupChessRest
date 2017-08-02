package com.capgemini.chess.service;

import java.util.List;

import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.to.ChallengeTO;

public interface ChallengesService {

	
	public List<ChallengeTO> suggestFivePossibleChallenges(Long myselfId) throws NoSuchUserException;

	public ChallengeTO setupChallenge(Long defendingId, Long callingId);

	public List<ChallengeTO> getChallengesByStatus(ChallengeStatus challengeStatus);

	public void deleteChallenge(ChallengeTO challenge);
}
