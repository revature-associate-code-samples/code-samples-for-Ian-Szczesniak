package com.revature.menus;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.revature.app.AppManager;
import com.revature.model.Client;
import com.revature.service.ClientService;
import org.apache.log4j.Logger;

public class Menus {
	final static Logger log = Logger.getLogger(Menus.class);
	
	public static void mainMenu() {
		int choice = 0;
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Main Menu\n1) Log-in\n2) Register\n3) Exit");
			
			try {
				choice = sc.nextInt();
			}
			catch (InputMismatchException ime) {
				System.out.println("Please choose an option from the Main Menu. Enter #\n");
				//consume the new line character from enter that is left by calling nextInt
				sc.nextLine();
				continue;
			}
			
			//Login
			if (choice==1) {
				Client clientInfo = AppManager.login();
				if (clientInfo==null||clientInfo.getAuth() == null){
					System.out.println("Incorrect username and password. Please try again.\n");
					continue;
				}
				else if (clientInfo.getAuth().equals("admin")) {
					System.out.println("Welcome "+ clientInfo.getUserFirst() +" "+ clientInfo.getUserLast()+"! How can we help you?\n");
					log.info(clientInfo.getUserName()+" has logged in");
					adminMenu(clientInfo);
				}
				else if (clientInfo.getAuth().equals("client") && clientInfo.getApproval().equals("yes")) {
					System.out.println("Welcome "+ clientInfo.getUserFirst() +" "+ clientInfo.getUserLast()+"! How can we help you?\n");
					log.info(clientInfo.getUserName()+" has logged in");
					clientMenu(clientInfo);
				}
				else if (clientInfo.getAuth().equals("client")) {
					System.out.println("Sorry, your account needs to be approved before you can login.\n");
				}
			}
			
			//Register
			else if (choice==2) {
				//The bank account is created right after they register
				Client client = AppManager.register();
				AppManager.createBank(client);
				System.out.println("\nYou have been registered as a client.\nYou must be approved by an admin to access your bank account.\n");
				log.info("A new client has been registered");
			}
			
			//Exit
			else if (choice==3) {
				break;
			}
			
			//Inappropriate number
			else {
				System.out.println("Please choose an option from the Main Menu. Enter #\n");
				continue;
			}
		}
	}
	
	public static void clientMenu(Client clientInfo) {
		Scanner sc3 = new Scanner(System.in);
		int choice = 0;
		while (true) {
			System.out.println("Client Menu:\n1) View my Info\n2) Check my Funds\n3) Make a Transaction\n4) Log Out");
			
			try {
				choice = sc3.nextInt();
			}
			catch (InputMismatchException ime) {
				System.out.println("Please choose an option from the Main Menu. Enter #\n");
				//consume the new line character from enter that is left by calling nextInt
				sc3.nextLine();
				continue;
			}
			
			//View my Info
			if (choice==1) {
				System.out.println("First Name: "+clientInfo.getUserFirst()+
						"    Last Name: "+clientInfo.getUserLast()+"    Username: "+clientInfo.getUserName()+
						"    Account Type: "+clientInfo.getAuth()+
						"    Approval: "+clientInfo.getApproval()+"\n");
				continue;
			}
			
			//Check my Funds
			else if (choice==2) {
				Client clientFunds = AppManager.checkFunds(clientInfo);
				int funds = clientFunds.getFunds();
				System.out.println("You have $"+funds+" in your bank account.\n");
			}
			
			//Make a Transaction
			else if (choice==3) {
				ClientService.getClientService().transfer(clientInfo);
			}
			
			//Log Out
			else if (choice==4) {
				System.out.println("You have been logged out.\n");
				log.info(clientInfo.getUserName()+" has logged out.");
				break;
			}
			
			//Inappropriate number
			else {
				System.out.println("Please choose an option from the Login Menu. Enter #\n");
				continue;
				}
			}
	}
	
	public static void adminMenu(Client clientInfo) {
		Scanner sc4 = new Scanner(System.in);
		int choice = 0;
		while (true) {
			System.out.println("Admin Menu:\n1) View my Info\n2) Approve Client Account\n3) Create an Administrator\n4) Check my Funds\n5) Make a Transaction\n6) Log Out");
			try {
				choice = sc4.nextInt();
			}
			catch (InputMismatchException ime) {
				System.out.println("Please choose an option from the Main Menu. Enter #\n");
				//consume the new line character from enter that is left by calling nextInt
				sc4.nextLine();
				continue;
			}
			
			//View my Info
			if (choice==1) {
				System.out.println("First Name: "+clientInfo.getUserFirst()+
						"    Last Name: "+clientInfo.getUserLast()+"    Username: "+clientInfo.getUserName()+
						"    Account Type: "+clientInfo.getAuth()+
						"    Approval: "+clientInfo.getApproval()+"\n");
			}
			
			//Approve Client Account
			else if (choice==2) {
				List<Client> clientList = ClientService.getClientService().listAllClients();
				String user="";
				outer: while(true) {
					//This if block takes in the username to be approved from the scanner
					//and determines whether they are eligible to be approved
					if (!user.equals("")) {
						for (int i=0;i<clientList.size();i++) {
							if (clientList.get(i).getUserName().equals(user)) {
								if (clientList.get(i).getApproval().equals("yes")) {
									System.out.println(clientList.get(i).getUserName()+" has already been approved!\n");
									break outer;
								}
								Client clientApproval = clientList.get(i);
								AppManager.approveID(clientApproval);
								System.out.println(clientApproval.getUserName()+" has been approved!\n");
								log.info(clientApproval.getUserName()+" has been approved by "+clientInfo.getUserName());
								break outer;
							}
						}
						System.out.println(user+" does not exist. Please try again.\n");
						break outer;
					}
					//Prints out the list of users and their info
					else if (user.equals("")){
						Scanner sc5 = new Scanner(System.in);
							for (int i=0;i<clientList.size();i++) {
								System.out.println("Username: "+clientList.get(i).getUserName()+
										"   Account Type: "+clientList.get(i).getAuth()+
										"   Approval: "+clientList.get(i).getApproval());
									}
							System.out.println("Enter the username you would like to approve: ");
							user = sc5.nextLine();
						}
					}
			}
			
			//Create an Administrator
			else if (choice==3) {
				//The bank account is created right after they register
				Client admin = AppManager.registerAdmin();
				AppManager.createBank(admin);
				System.out.println("\nYou have been registered as an admin.\n");
				log.info("A new administrator has been created.");
				break;
			}
			
			//Check my Funds
			else if (choice==4) {
				Client clientFunds = AppManager.checkFunds(clientInfo);
				int funds = clientFunds.getFunds();
				System.out.println("You have $"+funds+" in your bank account.\n");
			}
			
			//Make a Transaction
			else if (choice==5) {
				ClientService.getClientService().transfer(clientInfo);
			}
			
			//Log Out
			else if (choice==6) {
				System.out.println("You have been logged out.\n");
				log.info(clientInfo.getUserName()+" has logged out.");
				break;
			}
			
			//Inappropriate number
			else {
				System.out.println("Please choose an option from the Login Menu. Enter #\n");
				continue;
			}
		}
	}
}
