package com.kodbook.Service;

import com.kodbook.Entity.User;

public interface UserService  {

	boolean userExists(String username, String email);

	void addUser(User user);

	boolean validateUser(String username, String password);

}
