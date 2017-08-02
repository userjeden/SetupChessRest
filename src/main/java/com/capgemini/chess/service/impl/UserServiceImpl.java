package com.capgemini.chess.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.chess.service.ChallengesService;
import com.capgemini.chess.service.NoSuchUserException;
import com.capgemini.chess.service.ReadStatsService;
import com.capgemini.chess.service.UserService;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.StatsTO;

@Service
public class UserServiceImpl implements UserService {
	

	private ReadStatsService readStatServ;
	private ChallengesService challengeService;
	
	@Autowired
	public UserServiceImpl(ReadStatsService readStatServ, 
			ChallengesService challengeService) {
		this.readStatServ = readStatServ;
		this.challengeService = challengeService;
	}
	
	
	@Override
	public List<ChallengeTO> suggestFivePossibleChallenges(Long myselfId) throws NoSuchUserException {
		return challengeService.suggestFivePossibleChallenges(myselfId);
	}

	@Override
	public ChallengeTO setupChallenge(Long defendingId, Long callingId) throws NoSuchUserException {
		return challengeService.setupChallenge(defendingId, callingId);
	}

	@Override
	public List<StatsTO> showUserStats(Long[] userIds) throws NoSuchUserException {
		return readStatServ.getStatsByID(userIds);
	}

}
