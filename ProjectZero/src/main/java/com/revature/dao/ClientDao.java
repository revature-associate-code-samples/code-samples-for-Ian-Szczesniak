package com.revature.dao;

import java.util.List;
import com.revature.model.Client;

public interface ClientDao {
	
	public boolean insertClient(Client client);
	public boolean userExist(String username);
	public boolean bankCreation(Client client);
	public Client getClient(Client client);
	public List<Client> getAllClients();
	public void approveID(Client client);
	public Client checkFunds(Client client);
	public Client transaction(Client client);
	public int withdrawal(int funds);
	public int deposit(int funds);

}
