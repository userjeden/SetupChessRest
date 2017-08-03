package com.capgemini.chess.dataaccess.dao.impl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.capgemini.chess.DatabaseInstCounter;
import com.capgemini.chess.dataaccess.dao.ChallengeDao;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.mapper.CommonMapper;
import com.capgemini.chess.service.to.ChallengeTO;

@Repository
public class ChallengeDaoImpl implements ChallengeDao {

	private final Set<ChallengeEntity> challenges;

	public ChallengeDaoImpl() {
		this.challenges = new HashSet<>();
		int num = DatabaseInstCounter.challengeDaoCount ++;
		System.err.println("CHALLENGE REPOSITORY INSTANCE: " + num);
		this.challenges.forEach(System.err::println);
	}
	
	public ChallengeDaoImpl(Set<ChallengeEntity> challenges) {
		this.challenges = challenges;
		int num = DatabaseInstCounter.challengeDaoCount ++;
		System.err.println("CHALLENGE REPOSITORY INSTANCE: " + num);
		this.challenges.forEach(System.err::println);
	}
	

	@Override
	public Set<ChallengeTO> findByDefendingUser(Long defendingId, ChallengeStatus status) {
		return challenges.stream().filter(c -> (c.getDefendingUser().equals(defendingId) 
				&& c.getStatus().equals(status))).map(CommonMapper::map).collect(Collectors.toSet());
	}
	
	@Override
	public boolean checkIfContraChallengeExists(Long defendingId, Long callingId) {
		return challenges.stream().anyMatch(c -> (c.getDefendingUser().equals(callingId) 
				&& c.getCallingUser().equals(defendingId) && c.getStatus() == ChallengeStatus.THROWN));
	}
	
	@Override
	public ChallengeTO acceptExistContraChallenge(Long defendingId, Long callingId) {
		ChallengeEntity challenge = challenges.stream().filter(c -> (c.getDefendingUser().equals(callingId)
				&& c.getCallingUser().equals(defendingId) && c.getStatus() == ChallengeStatus.THROWN)).findFirst().orElse(null);
		challenge.setStatus(ChallengeStatus.ACCEPTED);
		return CommonMapper.map(challenge);
	}

	@Override
	public ChallengeTO setupNewChallenge(Long defendingId, Long callingId) {
		ChallengeEntity challenge = new ChallengeEntity();
		challenge.setDefendingUser(defendingId);
		challenge.setCallingUser(callingId);
		challenge.setStatus(ChallengeStatus.THROWN);
		challenges.add(challenge);
		return CommonMapper.map(challenge);
	}

	@Override
	public List<ChallengeTO> getChallengesByStatus(ChallengeStatus challengeStatus) {
		return challenges.stream().filter(c -> c.getStatus().equals(challengeStatus)).
				map(CommonMapper::map).collect(Collectors.toList());
	}

	@Override
	public void deleteChallenge(ChallengeTO challenge){
		ChallengeEntity forRemoval = challenges.stream().
				filter(c -> (c.getCallingUser().equals(challenge.getCallingUser()) &&
						c.getDefendingUser().equals(challenge.getDefendingUser()) &&
						c.getStatus().equals(challenge.getStatus()) )).findFirst().orElse(null);
		if(forRemoval != null){
			challenges.remove(forRemoval);
		}
	}


	
}
