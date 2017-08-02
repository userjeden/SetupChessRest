package com.capgemini.chess.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.chess.dataaccess.dao.UserDao;
import com.capgemini.chess.service.NoSuchUserException;
import com.capgemini.chess.service.ReadUserService;
import com.capgemini.chess.service.to.UserTO;

@Service
public class ReadUserServiceImpl implements ReadUserService {

	private UserDao userDao;
	
	@Autowired
	public ReadUserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	@Override
	public UserTO getUserByID(Long id) throws NoSuchUserException {
		UserTO user = userDao.readUserByID(id);
		if(user == null){
			throw new NoSuchUserException("non-existing user: " + id);
		}
		return user;
	}

	
	@Override
	public UserTO getUserByLogin(String login) throws NoSuchUserException {
		UserTO user = userDao.readUserByLogin(login);
		if(user == null){
			throw new NoSuchUserException("non-existing user: " + login);
		}
		return user;
	}
	

	@Override
	public List<UserTO> getUsersByRank(int rank, int range) {
		return userDao.readUsersByRank(rank, range);
	}
	
}
