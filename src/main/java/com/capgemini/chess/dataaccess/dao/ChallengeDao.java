package com.capgemini.chess.dataaccess.dao;
import java.util.List;
import java.util.Set;
import com.capgemini.chess.enums.ChallengeStatus;
import com.capgemini.chess.service.to.ChallengeTO;

public interface ChallengeDao {

	
	public Set<ChallengeTO> findByDefendingUser(Long defndingId, ChallengeStatus status);

	public boolean checkIfContraChallengeExists(Long defndingId, Long callingId);

	public ChallengeTO acceptExistContraChallenge(Long defndingId, Long callingId);

	public ChallengeTO setupNewChallenge(Long defndingId, Long callingId);

	public List<ChallengeTO> getChallengesByStatus(ChallengeStatus challengeStatus);

	public void deleteChallenge(ChallengeTO challenge);
}
