package com.revature.daos;

import java.util.List;
import com.revature.models.User;

public interface UserDao {
	
	public int addUser(User user);
	public boolean checkUser(User user);
	public User getUser(User user);
	public List<User> getAllUsers();
	public User updateUser(User user);
	
}
