package com.capgemini.chess.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.ChallengesService;
import com.capgemini.chess.service.NoSuchChallException;
import com.capgemini.chess.service.UpkeepService;
import com.capgemini.chess.service.to.ChallengeTO;

@Service
public class UpkeepServiceImpl implements UpkeepService {
	

	private ChallengesService challengeService;
	
	@Autowired
	public UpkeepServiceImpl(ChallengesService challengeService) {
		this.challengeService = challengeService;
	}
	

	@Override
	public List<ChallengeTO> getChallengesByStatus(ChallengeStatus challengeStatus) throws NoSuchChallException {
		return challengeService.getChallengesByStatus(challengeStatus);
	}


	@Override
	public void deleteChallenge(ChallengeTO challenge) throws NoSuchChallException {
		challengeService.deleteChallenge(challenge);
	}

}
