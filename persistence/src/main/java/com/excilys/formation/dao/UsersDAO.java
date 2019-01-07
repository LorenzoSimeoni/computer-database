package com.excilys.formation.dao;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.model.Users;

@Repository
public class UsersDAO {
	
	private static final String FINDUSER = "FROM Users WHERE name = :name AND password = :password";
	private static final String FINDUSERBYUSERNAME = "FROM Users WHERE name = :name";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Optional<Users> findUser(String name, String password) {
		Session session = sessionFactory.openSession();
		Users user = session.createQuery(FINDUSER, Users.class)
				.setParameter("name", name)
				.setParameter("password", password)
				.getSingleResult();
		session.close();
		return Optional.ofNullable(user);
	}
	
	public Optional<Users> findUserByUsername(String name) {
		Session session = sessionFactory.openSession();
		Users user = session.createQuery(FINDUSERBYUSERNAME, Users.class)
				.setParameter("name", name)
				.getSingleResult();
		session.close();
		return Optional.ofNullable(user);
	}
	
	
	
}
