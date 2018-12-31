package com.revature.daos;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class UserDaoImpl implements UserDao {
	
	private static UserDao ud;

	public UserDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static UserDao getDao() {
		if (ud == null) {
			ud = new UserDaoImpl();
		}
		return ud;
	}

	@Override
	public int addUser(User user) {
		int userPK = 0;
		if (checkUser(user)) {
			//-1 indicates that the username has already been taken by another user
			return -1;
		}
		Session hiSess = HibernateUtil.getSession();
		Transaction tx = hiSess.beginTransaction();
		// must add address first if not already in db to avoid transient exception
		AdrDao ad = AdrDaoImpl.getDao();
		if (ad.getAddress(user.getAddress().getAdr_id()) == null) { // if adr not in db
			AdrDaoImpl.getDao().addAdr(user.getAddress()); // or addAdr - if session can be opened within a session
		}
		try {
			userPK = (int) hiSess.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();
		hiSess.close();
 		return userPK;
	}
	
	@Override
	public boolean checkUser(User user) {
		Session hiSess = HibernateUtil.getSession();
		String hql = "from User where username = :userVal";
		Query<User> q = hiSess.createQuery(hql, User.class);
		q.setParameter("userVal", user.getUsername());
		try {
			//checking if a user was returned from the database
			User user2 = q.getSingleResult();
			if (user2==null) { return false; }
			} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
		
	@Override
	public User getUser(User user) {
		
		Session hiSess = HibernateUtil.getSession();
		// HQL uses bean name, NOT table name
		String hql = "FROM User WHERE username = :userVal AND pswd = :pwVal";
		Query<User> selectUser = hiSess.createQuery(hql, User.class);
		selectUser.setParameter("userVal", user.getUsername());
		selectUser.setParameter("pwVal", user.getPswd());
		try {
			user = (User) selectUser.getSingleResult();
			hiSess.close();
			return user;
		} catch (NoResultException nre) {
			nre.printStackTrace(); // use logging
		}
		hiSess.close();
		return null;
	}	

	@Override
	public List<User> getAllUsers() {
		Session hiSess = HibernateUtil.getSession();
		Query<User> selectAll = hiSess.createQuery("FROM User", User.class);
		List<User> users = selectAll.list();
		hiSess.close();
		return users;
	}
	
	//update user would be used to invalidate a user as well
	@Override
	public User updateUser(User user) {
		Session hiSess = HibernateUtil.getSession();
		Transaction tx = hiSess.beginTransaction();
		try {
			hiSess.update(user);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		tx.commit();
		hiSess.close();
		return user;
	}

	
	
}
