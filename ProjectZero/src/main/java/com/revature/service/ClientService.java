package com.revature.service;

import java.util.List;

import com.revature.dao.ClientImplementation;
import com.revature.model.Client;

public class ClientService {

	private static ClientService clientService;
	private ClientService() {
	}
	
	public static ClientService getClientService() {
		if(clientService == null) {
			clientService = new ClientService();
		}
		return clientService;
	}
	
	public boolean bankCreation(Client client) {
		return ClientImplementation.getClientDao().bankCreation(client);
	}
	
	public Client getClientDetails(Client client){
		return ClientImplementation.getClientDao().getClient(client);
	}
	
	public Client checkBankFunds(Client client) {
		return ClientImplementation.getClientDao().checkFunds(client);
	}
	
	public List<Client> listAllClients(){
		return ClientImplementation.getClientDao().getAllClients();
	}
	public boolean registerClient(Client client) {
		return ClientImplementation.getClientDao().insertClient(client);
	}
	
	public void approveID(Client client) {
		ClientImplementation.getClientDao().approveID(client);
	}
	
	public Client transfer(Client client) {
		return ClientImplementation.getClientDao().transaction(client);
	}
	
}
