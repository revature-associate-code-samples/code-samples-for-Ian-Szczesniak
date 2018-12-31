package com.revature.app;

import java.util.Scanner;

import com.revature.dao.ClientImplementation;
import com.revature.model.Client;
import com.revature.service.ClientService;
import org.apache.log4j.Logger;

public class AppManager {
	final static Logger log = Logger.getLogger(ClientImplementation.class);
	
	public static Client register() {
		Scanner sc2 = new Scanner(System.in);
		while (true) {
			try {
				//User info to be passed into the database
				System.out.println("Please enter your first name.");
				String userFirst = sc2.nextLine();
				System.out.println("Please enter you last name.");
				String userLast=sc2.nextLine();
				System.out.println("Please enter your username.");
				String userName=sc2.nextLine();
				System.out.println("Please enter your password.");
				String userPass=sc2.nextLine();
				//The newly added user is a client that is unable to log in until approved
				String approval="no";
				String auth = "client";
				
				Client register = new Client(userFirst, userLast, userName, userPass, approval, auth);
				ClientService.getClientService().registerClient(register);
				return register;
			}
			catch (Exception e) {
				System.out.println("Please try again");
				//consume the new line character from enter left by calling next int
				sc2.nextLine();
			}
		}
	}
	
	public static Client registerAdmin() {
		Scanner sc2 = new Scanner(System.in);
		while (true) {
			try {
				//User info to be passed into the database
				System.out.println("Please enter your first name.");
				String userFirst = sc2.nextLine();
				System.out.println("Please enter you last name.");
				String userLast=sc2.nextLine();
				System.out.println("Please enter your username.");
				String userName=sc2.nextLine();
				System.out.println("Please enter your password.");
				String userPass=sc2.nextLine();
				//Any account the admin creates is also an admin and is pre-approved
				String approval="yes";
				String auth = "admin";
				
				Client register = new Client(userFirst, userLast, userName, userPass, approval, auth);
				ClientService.getClientService().registerClient(register);
				return register;
			}
			catch (Exception e) {
				System.out.println("Please try again");
			}
		}
	}
	
	public static Client checkFunds(Client client) {
		return ClientService.getClientService().checkBankFunds(client);
	}
	
	public static void createBank(Client client) {
		ClientService.getClientService().bankCreation(client);
	}
	
	public static Client login() {
		//
		Scanner sc5 = new Scanner(System.in);
		while (true) {
			Client clientInfo = null;
			try {
				System.out.println("Username:");
				String userName=sc5.nextLine();
				System.out.println("Password:");
				String userPass=sc5.nextLine();
				
				Client login = new Client(userName, userPass);
				clientInfo = ClientService.getClientService().getClientDetails(login);
				return clientInfo;
			}
				catch (Exception e) {
				System.out.println("Incorrect username and password. Please try again.\n");
				return clientInfo;
			}
		}
	}
	
	public static void approveID(Client client) {
		ClientService.getClientService().approveID(client);
	}
}
