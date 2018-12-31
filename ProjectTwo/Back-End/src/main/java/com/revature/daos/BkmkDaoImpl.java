package com.revature.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.revature.models.Bookmark;
import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class BkmkDaoImpl implements BkmkDao {
	
	private static BkmkDao bd; // will typing as interface work?

	public BkmkDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static BkmkDao getDao() {
		if (bd == null) {
			bd = new BkmkDaoImpl();
		}
		return bd;
	}

	/*We dont need this as of right now
	 * @Override
	public Bookmark getBkmk(User user) {
		Session hiSess = HibernateUtil.getSession();
		// HQL uses bean name, NOT table name
		String hql = "FROM Bookmark WHERE book_id = :idVal";
		Query<Bookmark> selectBkmk = hiSess.createQuery(hql, Bookmark.class);
		selectBkmk.setParameter("idVal", user.getUser_id());
		Bookmark bkmk = (Bookmark) selectBkmk.getSingleResult(); // exception needs handling
		hiSess.close();
		return bkmk;
	}*/

	@Override
	public List<Bookmark> getAllBkmks(User user) {
		List<Bookmark> bkmks = new ArrayList<Bookmark>();
		Session hiSess = HibernateUtil.getSession();
		String hql = "FROM Bookmark WHERE user_id = :uIdVal";
		Query<Bookmark> selectBkmks = hiSess.createQuery(hql, Bookmark.class);
		selectBkmks.setParameter("uIdVal", user.getUser_id());
		try {
			bkmks = selectBkmks.list();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		hiSess.close();
		return bkmks;
	}

	@Override
	public int addBkmk(Bookmark bkmk) {
		int bkmkPK=0;
		Session hiSess = HibernateUtil.getSession();
		Transaction tx = hiSess.beginTransaction();
		try {
			bkmkPK = (int) hiSess.save(bkmk);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		tx.commit();
		hiSess.close();
 		return bkmkPK;
	}

	@Override
	public void removeBkmk(Bookmark bkmk) {
		Session hiSess = HibernateUtil.getSession();
		Transaction tx = hiSess.beginTransaction();
		try {
			hiSess.delete(bkmk);
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		tx.commit();
		hiSess.close();
	}
	
	
	
	
	

}
