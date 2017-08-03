package com.capgemini.chess.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.StatsDao;
import com.capgemini.chess.service.NoSuchUserException;
import com.capgemini.chess.service.ReadStatsService;
import com.capgemini.chess.service.to.StatsTO;

@Service
public class ReadStatsServiceImpl implements ReadStatsService {

	private StatsDao statsDao;
	
	@Autowired
	public ReadStatsServiceImpl(StatsDao statsDao) {
		this.statsDao = statsDao;
	}
	

	@Override
	public StatsTO getStatsByID(Long id) throws NoSuchUserException {
		StatsTO stats = statsDao.readStatsByID(id);
		if(stats == null){
			throw new NoSuchUserException("non-existing user: " + id);
		}
		return stats;
	}

	
	@Override
	public StatsTO getStatsByLogin(String login) throws NoSuchUserException {
		StatsTO stats = statsDao.readStatsByLogin(login);
		if(stats == null){
			throw new NoSuchUserException("non-existing user: " + login);
		}
		return stats;
	}


	@Override
	public List<StatsTO> getStatsByID(Long[] userIds) throws NoSuchUserException {
		List<StatsTO> statsList = statsDao.readStatsByID(userIds);
		for(StatsTO stats : statsList){
			if(stats == null){
				throw new NoSuchUserException("non-existing user in array");
			}
		}
		return statsList;
	}

}
