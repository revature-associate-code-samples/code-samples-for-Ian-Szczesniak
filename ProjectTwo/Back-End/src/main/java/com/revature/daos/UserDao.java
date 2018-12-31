package com.revature.daos;

import java.util.List;
import com.revature.models.User;

public interface UserDao {
	
	public int addUser(User user);
	public boolean checkUser(User user);
	public User getUser(User user);
	public List<User> getAllUsers();
	public User updateUser(User user);
	//public boolean deleteUser(int id);
	
	// optional
	//public boolean setUserInfo(String field, String newValue);
	//public boolean updateAddress(int user_id, String newAddress);
	
}
