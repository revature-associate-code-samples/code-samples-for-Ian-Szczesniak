package com.revature.daos;

import com.revature.models.Address;

public interface AdrDao {

	public Address getAddress(int id);
	public int addAdr(Address adr);
	
}
