package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.revature.model.Client;
import com.revature.util.JDBCConnectionUtil;

public class ClientImplementation implements ClientDao {

	private static ClientImplementation clientDao;
	final static Logger log = Logger.getLogger(ClientImplementation.class);
	
	private ClientImplementation() {
	}
	
	public static ClientImplementation getClientDao(){
		if (clientDao == null){
			clientDao = new ClientImplementation();
		}
		return clientDao;
	}

	
	public boolean insertClient(Client client) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String exe = "call addClient(?,?,?,?,?,?)";
			PreparedStatement sp = conn.prepareCall(exe);
			sp.setString(1, client.getUserFirst());
			sp.setString(2, client.getUserLast());
			sp.setString(3, client.getUserName());
			sp.setString(4, client.getUserPass());
			sp.setString(5, client.getApproval());
			sp.setString(6, client.getAuth());
			sp.executeQuery();
			return userExist(client.getUserName());
			
		} catch (SQLException s) {
			log.error("catch block in insertClient - Dao Implementation - occured");
		}
		return false;
	}
	
	
	public boolean userExist(String username) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String query = "select USERID from Clients where userName = ?";
			PreparedStatement check = conn.prepareStatement(query);
			check.setString(1, username);
			ResultSet rs=check.executeQuery();
			while (rs.next()) {
				if (rs.getInt("userid")>0) {
					return true;
				}
			}
			return false;
			
		} catch (SQLException s) {
			log.error("catch block in userExist - Dao Implementation - occured");
		}
		return true;
	}

	
	public boolean bankCreation(Client client) {
		int id=0;
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			//Grab the userID from the database for the client
			String query = "select USERID from Clients where userName = ?";
			PreparedStatement lookup = conn.prepareStatement(query);
			lookup.setString(1, client.getUserName());
			ResultSet rs = lookup.executeQuery();
			while (rs.next()) {
				id = rs.getInt("userid");
			}
			
			//Create a bank account for that userID
			String exe = "call addBank(?,?)";
			PreparedStatement sp = conn.prepareCall(exe);
			sp.setInt(1, id);
			sp.setDouble(2, 0);
			sp.executeQuery();
			return true;
			
		} catch (SQLException s) {
			log.error("catch block in bankCreation - Dao Implementation - occured");
		}
		return false;
	}

	
	public Client getClient(Client client) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String query = "select * from clients where USERNAME=? and USERPASS=?";
			PreparedStatement lookup = conn.prepareStatement(query);
			lookup.setString(1, client.getUserName());
			lookup.setString(2, client.getUserPass());
			ResultSet rs = lookup.executeQuery();
			while (rs.next()) {
				return new Client(rs.getInt("userid"), 
						rs.getString("userfn"), 
						rs.getString("userln"),
						rs.getString("username"), 
						rs.getString("userpass"),
						rs.getString("approval"),
						rs.getString("auth"));
			}
		}
		catch (SQLException s) {
			log.error("catch block in getClient - Dao Implementation - occured");
		}
		return new Client();
	}

	
	public List<Client> getAllClients() {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String query = "select * from clients";
			PreparedStatement lookup = conn.prepareStatement(query);
			ResultSet rs = lookup.executeQuery();
			List<Client> clientList = new ArrayList<>();
			while (rs.next()) {
				clientList.add(new Client(rs.getInt("userid"), 
						rs.getString("userfn"), 
						rs.getString("userln"),
						rs.getString("userName"), 
						rs.getString("userPass"),
						rs.getString("approval"),
						rs.getString("auth")));
			}
			return clientList;
		} 
		catch (SQLException s) {
			log.error("catch block in getAllClients - Dao Implementation - occured");
		}
		return new ArrayList<>();
	}
	
	
	public void approveID(Client client) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String exe = "call approveUser(?)";
			PreparedStatement sp = conn.prepareCall(exe);
			sp.setString(1, client.getUserName());
			sp.executeQuery();
		} catch (SQLException s) {
			log.error("catch block in approveID - Dao Implementation - occured");
		}
	}
	
	
	public Client checkFunds(Client client) {
		//A new client object is created and populated with the userID and
		//funds that are associated with the client
			Client clientFunds = new Client();
			try (Connection conn = JDBCConnectionUtil.getConnection()) {
				String query = "select * from bank where userid = ?";
				PreparedStatement lookup = conn.prepareStatement(query);
				lookup.setInt(1, client.getUserId());
				ResultSet rs = lookup.executeQuery();
				while (rs.next()) {
					clientFunds.setUserId(rs.getInt("userid"));
					clientFunds.setFunds(rs.getInt("funds"));
				}
				return clientFunds;
			} 
			catch (SQLException e) {
				log.error("catch block in checkFunds - Dao Implementation - occured");
			}
			return clientFunds;
		}
	
	
	public Client transaction(Client client) {
		//A new client object is created and populated with the userID and
		//funds that are associated with the client
		Scanner sc = new Scanner(System.in);
		Client clientTransaction = new Client();
		int total;
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			int funds=0;
			String query = "Select funds from bank where userID = ?";
			PreparedStatement lookup = conn.prepareStatement(query);
			lookup.setInt(1, client.getUserId());
			ResultSet rs = lookup.executeQuery();
			while (rs.next()) {
				funds=rs.getInt("funds");
			}
			//The Transaction methods was split up to handle both withdrawals and deposits
			while(true) {
				System.out.println("Would you like to make a withdrawal or a deposit?\n1) Withdrawal\n2) Deposit");
				int choice=0;
				try {
					choice = sc.nextInt();
				}
				catch (InputMismatchException ime) {
					System.out.println("Please choose to make a withdrawal (1) or a deposit (2). Enter #\n");
					sc.nextLine();
					continue;
				}
				//funds is the amount of money already in their account and is
				//passed through to the withdrawal and deposit method 
				if (choice==1) {
					total=withdrawal(funds);
					log.info("A withdrawal was made and the new total in the account is $"+total);
					break;
				}
				else if (choice==2) {
					total=deposit(funds);
					log.info("A deposit was made and the new total in the account is $"+total);
					break;
				}
				else {
					System.out.println("Please choose to make a withdrawal (1) or a deposit (2). Enter #\n");
					continue;
				}
			}
			String exe = "call updateFunds(?,?)";
			PreparedStatement sp = conn.prepareCall(exe);
			sp.setInt(1, total);
			sp.setInt(2, client.getUserId());
			sp.executeQuery();
			//populating the new client object
			clientTransaction.setUserId(client.getUserId());
			clientTransaction.setFunds(total);

		} catch (SQLException s) {
			log.error("catch block in transaction - Dao Implementation - occured");
		}
		return clientTransaction;
	}
	
	
	public int withdrawal(int funds) {
		int amount=0;
		int total=0;
		Scanner sc = new Scanner(System.in);
		if (funds==0) {
			System.out.println("You can not make a withdrawal because your balance is $0.\n");
			return amount;
		}
		while (true) {
			System.out.println("How much money would you like to withdraw?");
			try {
				amount=sc.nextInt();
			}
			catch (InputMismatchException ime) {
				System.out.println("Please enter a number.\n");
				sc.nextLine();
				continue;
			}
			if (funds-amount<0) {
				System.out.println("Sorry, you only have $"+funds+" in your account.\n");
				continue;
			}
			else if (amount<0) {
				System.out.println("Please enter a number greater than 0.\n");
				continue;
			}
			//if you do not want to make a withdrawal, you can enter 0
			else if (amount>=0) {
				total=funds-amount;
				System.out.println("Your new balance is $"+ (total));
				return total;
			}
		}
	}
	
	
	public int deposit(int funds) {
		int amount=0;
		int total=funds;
		Scanner sc = new Scanner(System.in);
		System.out.println("How much money would you like to deposit?");
		while (true) {
			try {
				amount=sc.nextInt();
			}
			catch (InputMismatchException ime) {
				System.out.println("Please enter how much you would like to deposit. Enter a number\n");
				sc.nextLine();
				continue;
			}
			if (amount>=0) {
				total=total+amount;
				System.out.println("Your new balance is $"+ total);
				return total;
			}
			else {
				System.out.println("Please enter a number greater than 0.\n");
				continue;
			}
		}
	}
}
