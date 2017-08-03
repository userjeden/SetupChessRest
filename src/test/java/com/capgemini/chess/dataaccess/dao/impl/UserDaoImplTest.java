package com.capgemini.chess.dataaccess.dao.impl;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.capgemini.chess.DatabaseMockupObject;
import com.capgemini.chess.dataaccess.dao.UserDao;
import com.capgemini.chess.service.to.UserTO;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

	private UserDao userDao;

	@Before
	public void prepareUserDao(){
		DatabaseMockupObject daoConfig = new DatabaseMockupObject();
		userDao = daoConfig.prepareUserDao();
	}
	
	
	@Test
	public void shouldReadUserByID(){
		
		// when
		UserTO user = userDao.readUserByID(3L);

		// then
		assertEquals("rocky", user.getLogin());
	}
		
	
	@Test
	public void shouldReadUserByLogin(){
		
		// when
		UserTO user = userDao.readUserByLogin("rocky");

		// then
		assertEquals(Long.valueOf(3), user.getId());
	}
	

	@Test
	public void shouldReadUsersByRank(){
		
		// when
		List<UserTO> users = userDao.readUsersByRank(5, 2);

		// then
		assertEquals(3, users.size());
	}
	
	
	@Test
	public void shouldNOTReadUserByIDreturnNull() {
		
		// when
		UserTO user = userDao.readUserByID(6L);

		// then
		assertTrue(user == null);
	}
	
}

