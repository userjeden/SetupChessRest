package com.capgemini.chess.dataaccess.dao.impl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

import com.capgemini.chess.DatabaseInstCounter;
import com.capgemini.chess.dataaccess.dao.StatsDao;
import com.capgemini.chess.dataaccess.entities.UserEntity;
import com.capgemini.chess.service.mapper.CommonMapper;
import com.capgemini.chess.service.to.StatsTO;

@Repository
public class StatsDaoImpl implements StatsDao {

	private final Set<UserEntity> users;

	public StatsDaoImpl() {
		this.users = new HashSet<>();
		int num = DatabaseInstCounter.statsDaoCount ++;
		System.err.println("STATS REPOSITORY INSTANCE: " + num);
		this.users.forEach(System.err::println);
	}
	
	public StatsDaoImpl(Set<UserEntity> users){
		this.users = users;
		int num = DatabaseInstCounter.statsDaoCount ++;
		System.err.println("STATS REPOSITORY INSTANCE: " + num);
		this.users.forEach(System.err::println);
	}
	
	
	@Override
	public StatsTO readStatsByID(Long id) {
		UserEntity user = users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
		return CommonMapper.mapForStats(user);
	}
	
	@Override
	public StatsTO readStatsByLogin(String login) {
		UserEntity user = users.stream().filter(u -> u.getLogin().equals(login)).findFirst().orElse(null);
		return CommonMapper.mapForStats(user);
	}

	@Override
	public List<StatsTO> readStatsByID(Long[] ids) {
		List<StatsTO> userList = new ArrayList<>();
		for(Long id : ids){
			userList.add(readStatsByID(id));
		}
		return userList;
	}

}
