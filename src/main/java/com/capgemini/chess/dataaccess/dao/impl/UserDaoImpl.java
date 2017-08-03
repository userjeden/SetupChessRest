package com.capgemini.chess.dataaccess.dao.impl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.capgemini.chess.DatabaseInstCounter;
import com.capgemini.chess.dataaccess.dao.UserDao;
import com.capgemini.chess.dataaccess.entities.UserEntity;
import com.capgemini.chess.service.mapper.CommonMapper;
import com.capgemini.chess.service.to.UserTO;

@Repository
public class UserDaoImpl implements UserDao {

	private final Set<UserEntity> users;

	public UserDaoImpl() {
		this.users = new HashSet<>();
		int num = DatabaseInstCounter.userDaoCount ++;
		System.err.println("USER REPOSITORY INSTANCE: " + num);
		this.users.forEach(System.err::println);
	}
	
	public UserDaoImpl(Set<UserEntity> users){
		this.users = users;
		int num = DatabaseInstCounter.userDaoCount ++;
		System.err.println("USER REPOSITORY INSTANCE: " + num);
		this.users.forEach(System.err::println);
	}

	
	@Override
	public UserTO readUserByID(Long id) {
		UserEntity user = users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
		return CommonMapper.map(user);
	}

	@Override
	public UserTO readUserByLogin(String login) {
		UserEntity user = users.stream().filter(u -> u.getLogin().equals(login)).findFirst().orElse(null);
		return CommonMapper.map(user);
	}

	@Override
	public List<UserTO> readUsersByRank(int rank, int range) {
		List<UserEntity> userEntities = users.stream().filter(u -> ((u.getUserValue() <= rank + range) 
				&& (u.getUserValue() >= rank - range))).collect(Collectors.toList());
		return CommonMapper.map2UserTOs(userEntities);
	}

}
