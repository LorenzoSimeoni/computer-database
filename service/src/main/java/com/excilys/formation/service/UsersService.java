package com.excilys.formation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.UsersDAO;
import com.excilys.formation.model.Users;

@Service
public class UsersService implements UserDetailsService {
	private UsersDAO usersDAO;
	
	@Autowired
	public UsersService(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}
	
	public Optional<Users> findUser(String name, String password) {
		return usersDAO.findUser(name, password);
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) {
    	Optional<Users> user = usersDAO.findUserByUsername(username);
    	UserBuilder builder = null;
    	if(!user.isPresent()) {
    		throw new UsernameNotFoundException(username);    		
    	} else {
    		builder = User.withUsername(username);
            builder.password(user.get().getPassword());
            builder.roles(user.get().getRole().getName());
    	}
        return builder.build();
    }
}
