package com.revature.model;

public class Client {
	
	private int userId;
	private String userFirst;
	private String userLast;
	private String userName;
	private String userPass;
	private String approval;
	private String auth;
	private int funds;
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Client(int userId, String userFirst, String userLast, String userName, String userPass, String approval, String auth) {
		super();
		this.userId = userId;
		this.userFirst = userFirst;
		this.userLast = userLast;
		this.userName = userName;
		this.userPass = userPass;
		this.approval = approval;
		this.auth = auth;
	}
	public Client(String userFirst, String userLast, String userName, String userPass, String approval, String auth) {
		super();
		this.userFirst = userFirst;
		this.userLast = userLast;
		this.userName = userName;
		this.userPass = userPass;
		this.approval = approval;
		this.auth = auth;
	}
	public Client(String userName, String userPass) {
		super();
		this.userName = userName;
		this.userPass = userPass;
	}
	
	public Client(int userId, int funds) {
		super();
		this.userId=userId;
		this.funds=funds;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserFirst() {
		return userFirst;
	}
	public void setUserFirst(String userFirst) {
		this.userFirst = userFirst;
	}
	public String getUserLast() {
		return userLast;
	}
	public void setUserLast(String userLast) {
		this.userLast = userLast;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public int getFunds() {
		return funds;
	}
	public void setFunds(int funds) {
		this.funds = funds;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approval == null) ? 0 : approval.hashCode());
		result = prime * result + ((auth == null) ? 0 : auth.hashCode());
		result = prime * result + funds;
		result = prime * result + ((userFirst == null) ? 0 : userFirst.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userLast == null) ? 0 : userLast.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userPass == null) ? 0 : userPass.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (approval == null) {
			if (other.approval != null)
				return false;
		} else if (!approval.equals(other.approval))
			return false;
		if (auth == null) {
			if (other.auth != null)
				return false;
		} else if (!auth.equals(other.auth))
			return false;
		if (funds != other.funds)
			return false;
		if (userFirst == null) {
			if (other.userFirst != null)
				return false;
		} else if (!userFirst.equals(other.userFirst))
			return false;
		if (userId != other.userId)
			return false;
		if (userLast == null) {
			if (other.userLast != null)
				return false;
		} else if (!userLast.equals(other.userLast))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPass == null) {
			if (other.userPass != null)
				return false;
		} else if (!userPass.equals(other.userPass))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Client [userId=" + userId + ", userFirst=" + userFirst + ", userLast=" + userLast + ", userName="
				+ userName + ", userPass=" + userPass + ", approval=" + approval + ", auth=" + auth + ", funds=" + funds
				+ "]";
	}
	
}
