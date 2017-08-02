package com.capgemini.chess.service;

import java.util.List;

import com.capgemini.chess.service.to.StatsTO;

public interface ReadStatsService {

	
	public StatsTO getStatsByID(Long id) throws NoSuchUserException;
	
	public StatsTO getStatsByLogin(String login) throws NoSuchUserException;

	public List<StatsTO> getStatsByID(Long[] userIds) throws NoSuchUserException;
}
