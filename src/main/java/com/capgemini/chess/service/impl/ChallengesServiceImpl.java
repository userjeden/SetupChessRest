package com.capgemini.chess.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.ChallengeDao;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.ChallengesService;
import com.capgemini.chess.service.NoSuchUserException;
import com.capgemini.chess.service.ReadUserService;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.UserTO;

@Service
public class ChallengesServiceImpl implements ChallengesService {

	private ChallengeDao challengeDao;
	private ReadUserService readUserService;

	private int expNumOfChallenges = 5;
	private int rankRange = 2;
	
	@Autowired
	public ChallengesServiceImpl(ChallengeDao challengeDao, ReadUserService readUserService) {
		this.challengeDao = challengeDao;
		this.readUserService = readUserService;
	}
	
	
	@Override
	public ChallengeTO setupChallenge(Long defendingId, Long callingId) {
		return (challengeDao.checkIfContraChallengeExists(defendingId, callingId)) ? 
				challengeDao.acceptExistContraChallenge(defendingId, callingId) : 
					challengeDao.setupNewChallenge(defendingId, callingId);
	}
	
	@Override
	public List<ChallengeTO> suggestFivePossibleChallenges(Long myselfId) throws NoSuchUserException {
		List<ChallengeTO> challenges = new ArrayList<>();
		challenges.addAll(readChallengesForUser(myselfId, ChallengeStatus.THROWN));
		challenges.addAll(prodChallengeSuggestions(myselfId, expNumOfChallenges - challenges.size()));
		return challenges;
	}
	
	@Override
	public List<ChallengeTO> getChallengesByStatus(ChallengeStatus challengeStatus) {
		return challengeDao.getChallengesByStatus(challengeStatus);
	}
	
	@Override
	public void deleteChallenge(ChallengeTO challenge) {
		challengeDao.deleteChallenge(challenge);
	}
	
	private Set<ChallengeTO> readChallengesForUser(Long myselfId, ChallengeStatus status){
		return challengeDao.findByDefendingUser(myselfId, status);
	}
	
	private Set<ChallengeTO> prodChallengeSuggestions(Long myselfId, int missingChallenges) throws NoSuchUserException {
		UserTO mySelf = mySelfUserLookup(myselfId);
		if(mySelf == null){
			throw new NoSuchUserException("non-existing user: " + myselfId);
		}
		List<UserTO> potentialOponents = readUserService.getUsersByRank(mySelf.getUserRank(), rankRange);
		potentialOponents.removeIf(u -> u.getId().equals(myselfId));
		Collections.shuffle(potentialOponents);
		
		Set<ChallengeTO> challengeSuggestions = new HashSet<>();
		for(int i = 0; i < missingChallenges; i++){
			if(potentialOponents != null && potentialOponents.size() > 0){
				UserTO oponent = potentialOponents.remove(0);
				ChallengeTO challenge = new ChallengeTO();
				challenge.setCallingUser(myselfId);
				challenge.setDefendingUser(oponent.getId());
				challenge.setStatus(ChallengeStatus.POTENTIAL);
				challengeSuggestions.add(challenge);
			}
		}
		return challengeSuggestions;
	}
	
	private UserTO mySelfUserLookup(long myselfId){
		UserTO mySelf = null;
		try {
			mySelf = readUserService.getUserByID(myselfId);
		} catch (NoSuchUserException e) {
		}
		return mySelf;
	}
	
}
