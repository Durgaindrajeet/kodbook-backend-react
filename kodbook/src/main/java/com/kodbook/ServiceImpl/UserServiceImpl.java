package com.kodbook.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodbook.Entity.User;
import com.kodbook.Repository.UserRepository;
import com.kodbook.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repo;
	

	@Override
	public void addUser(User user) {
	repo.save(user);
		
	}

	@Override
	public boolean userExists(String username, String email) {
		
		User user1 = repo.findByUsername(username);
		User user2 = repo.findByEmail(email);
		if(user1!= null || user2 != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateUser(String username, String password) {
		 String dbPass = repo.findByUsername(username).getPassword();
		 if(password.equals(dbPass)) {
			 return true;
		 }
		return false;
	}

	@Override
	public User getUser(String username) {
	
		return repo.findByUsername(username);
	}

	@Override
	public User updateUser(User user) {
		
		return repo.save(user);
	}
	public boolean resetPassword(String username, String newPassword) {
        User user = repo.findByUsername(username);
        if (user != null) {
            user.setPassword(newPassword);
            System.out.println("Password updated for " + username);
            repo.save(user);
            return true;
        }
        System.out.println("User not found for " + username);
        return false;
    }




}
