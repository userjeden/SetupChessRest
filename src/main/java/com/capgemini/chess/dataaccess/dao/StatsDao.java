package com.capgemini.chess.dataaccess.dao;
import java.util.List;

import com.capgemini.chess.service.to.StatsTO;

public interface StatsDao {
	

	public StatsTO readStatsByID(Long id);
	
	public StatsTO readStatsByLogin(String login);

	public List<StatsTO> readStatsByID(Long[] userIds);
}
