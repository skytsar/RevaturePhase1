package com.tesar.bank;

import com.tesar.bank.sql.BankQueries;
import com.tesar.bank.sql.PostresSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class User {
	public int id;
	public String username;
	private String password;
	public String firstName;
	public String lastName;
	public char position;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User(int id, String uname, String pword, String lname,String fname,String position) {
		super();
		this.id=id;
		this.username=uname;
		this.password=pword;
		this.firstName=fname;
		this.lastName=lname;
		this.position=position.toCharArray()[0];
		
		
	}
	
	public void customerOptions() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Hello "+firstName);
		System.out.println("(1) View accounts you own");
		System.out.println("(2) Create account");
		System.out.println("(3) Log out");
		int c = scanner.nextInt();
		switch(c) {
		case(1):
			List<Account> accountList=getAccountsWithID(this.id);
			if(accountList.isEmpty()) {
				System.out.println("You have no accounts.");
			}
			else {
				System.out.println("Account ID        Balance");
				for(Account account:accountList)
				System.out.println(account.accountId+ "       "+ account.ammount);
				System.out.println("Enter intended account");
				int accountChoice= scanner.nextInt();
				Account account=new Account(accountChoice);
				if(account.ownerId!=this.id) {
					System.out.println("This is not your account");
				}
				else {
					System.out.println("(0) Return");
					System.out.println("(1) Withdraw");
					System.out.println("(2) Deposit");
					System.out.println("(3) Transfer to another account");
					int actionChoice=scanner.nextInt();
					double entry=0.0;
					switch(actionChoice) {
					case 1:
						System.out.println("Enter the ammount you want to withdraw");
						entry =scanner.nextDouble();
						if(account.getAmmount()<entry || entry<0.0)
							System.out.println("Invalid request");
						else {
							account.withdrawl(entry);
						}
						break;
					case 2:
						System.out.println("Enter the ammount you want to deposit");
						entry =scanner.nextDouble();
						if(entry<0.0)
							System.out.println("Invalid request");
						else {
							account.deposit(entry);
						}
						
						break;
					default:
						System.out.println("Invalid input");
						
						
					}while(actionChoice!=0);
					
				}
				
			}
			
			break;
		
		case(2):
			System.out.println("What is your initial deposit?");
			double input = scanner.nextDouble();
			Account a= new Account();
			a.insertAccount(input,this.id);
			break;
		
		default:
			System.out.println("Invalid entry");
			break;
		}
		while(c!=3);
		
		
	}
	
	
	
	
	public List<Account> getAccountsWithID(int id){
		List<Account> accountList = new ArrayList();
		Connection connection=null;
		try  {
			connection = PostresSqlConnection.getConnection();
			String sql = BankQueries.GETACCOUNTSWITHOWNER;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Account account = new Account(rs.getInt("account_id"),rs.getDouble("balance"),id);
				accountList.add(account);
			}
			/*if(playerList.size()==0)
			{
				throw new BusinessException("No Player Records Available");
			}*/
		} catch (ClassNotFoundException  e) {
			System.out.println(e); // take off this line when in production
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		return accountList;
	}

}
