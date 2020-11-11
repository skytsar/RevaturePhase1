package com.tesar.bank;

import com.tesar.bank.DAO.BankDAO;
import com.tesar.bank.DAOimpl.BankDAOimpl;
import com.tesar.bank.sql.BankQueries;
import com.tesar.bank.sql.PostresSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.tesar.exception.BusinessException;
import java.util.InputMismatchException;


public class User {
	public int id;
	public String username;
	private String password;
	public String firstName;
	public String lastName;
	public char position;
	
	
	public User(int id, String uname, String pword, String lname,String fname,String position) {
		super();
		this.id=id;
		this.username=uname;
		this.password=pword;
		this.firstName=fname;
		this.lastName=lname;
		this.position=position.toCharArray()[0];
		
		
	}
	
	public void employeeOptions() {
		Scanner scanner = new Scanner(System.in);
		int c = 0;
		BankDAO bdbs=new BankDAOimpl();
		do {
		System.out.println("Hello "+firstName);
		System.out.println("(1) View pending accounts");
		System.out.println("(2) view transactions");
		System.out.println("(3) view all customers");
		System.out.println("(4) Log out");
		try {
			c = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {

		}
		//c = scanner.nextInt();
		switch(c) {
		case(1):
			try {
			List<Account> pendingList=bdbs.getPendingAccounts();
			if(pendingList.isEmpty())
				System.out.println("There are no pending account requests");
			else {
			int pendingChoice=-1;
			do {
			System.out.println("Account ID        Owner ID            Initial deposit");
			for(Account a:pendingList)
			System.out.println(a.accountId+ "                   "+a.ownerId+ "                   "+ a.ammount);
			System.out.println("Enter intended account ID (or enter 0 to cancel)");
			int occuranceCount=0;
			
			pendingChoice=scanner.nextInt();
			for(Account a:pendingList) {
				if(a.getAccountId()==pendingChoice) {
					occuranceCount++;
					System.out.println("Do you wish do accept this account? y/n");
					String yesno =scanner.next();
					switch(yesno) {
					case("y"):
						bdbs.deletePendingAccount(a.getAccountId());
						bdbs.insertAccount(a.getAmmount(), a.getOwnerId());
						System.out.println("Account accepted");
						pendingChoice=0;
						break;
					case("n"):
						bdbs.deletePendingAccount(a.getAccountId());
						System.out.println("Account rejected");
						pendingChoice=0;
						break;
					default:
						System.out.println("Entry must be either 'y' or 'n'");
						break;
					}
				}
			}
				if (occuranceCount==0)
				System.out.println("Number not in pending list");
			}while(pendingChoice!=0);
			}
			}
			catch(BusinessException e){
				
			}
			
			break;
		case(2):
			try {
				bdbs.printTransactions();
			}
			catch(BusinessException e) {
				
			}
			break;
		case(3):
			try {
				bdbs.printCustomers();
				System.out.println("Select account number");
				int x=scanner.nextInt();
				List<Account> accountList= bdbs.getAccountsWithID(x);
				if(accountList.isEmpty())
					System.out.println("This Customer has no accounts");
				else {
					System.out.println("Account ID        Balance");
					for(Account a:accountList)
					System.out.println(a.accountId+ "                       "+ a.ammount);
				}
				
			}
			catch(BusinessException e) {
				
			}
			
			
			break;
		case (4):
			break;
		default:
			System.out.println("Invalid entry");
			break;
		}
		}while(c!=4);
		
	}
	
	public void customerOptions() {
		Scanner scanner = new Scanner(System.in);
		BankDAO bdbs=new BankDAOimpl();
		int c = 0;
		do {
		System.out.println("Hello "+firstName);
		System.out.println("(1) View accounts you own");
		System.out.println("(2) Create account");
		System.out.println("(3) Log out");
		
			// = scanner.nextInt();
		
			try {
				c = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {

			}
		
			
		switch(c) {
		case(1):
			try {
				List<Account> accountList=bdbs.getAccountsWithID(this.id);
			
			
			Account account =null;
			if(accountList.isEmpty()) {
				System.out.println("You have no accounts.");
			}
			else {
				System.out.println("Account ID        Balance");
				for(Account a:accountList)
				System.out.println(a.accountId+ "                       "+ a.ammount);
				System.out.println("Enter intended account");
				//try {
				
					//try {
					
					try {
						//System.out.println("In try block");
						 int accountChoice= Integer.parseInt(scanner.nextLine());
						 account=bdbs.getAccountwithID(accountChoice);
						 if(account.ownerId!=this.id) {
							 throw new BusinessException("This is not your account");
						 }
						 
					//} catch (NumberFormatException e) {
						//System.out.println(e);
					//}
					
					
					
				
			
				
				
				
				
					int actionChoice=5;
					do {
					System.out.println("(0) Return");
					System.out.println("(1) Withdraw");
					System.out.println("(2) Deposit");
					System.out.println("(3) Transfer to another account");
					
					//try {
						//System.out.println("In try block");
						 actionChoice= Integer.parseInt(scanner.nextLine());
						 
					//} catch (NumberFormatException e) {
						//System.out.println(e);
					//}/
					double entry=0.0;
					int accountEntry;
					
					switch(actionChoice) {
					case 1:
						System.out.println("Enter the ammount you want to withdraw");
						entry=Double.parseDouble(scanner.nextLine());
						//entry =scanner.nextDouble();
						if(account.getAmmount()<entry || entry<0.0)
							System.out.println("Invalid request");
						else {
							account.withdrawl(entry);
						}
						break;
					case 2:
						System.out.println("Enter the ammount you want to deposit");
						entry=Double.parseDouble(scanner.nextLine());
						//entry =scanner.nextDouble();
						
						if(entry<0.0)
							System.out.println("Invalid request");
						else {
							account.deposit(entry);
						}
						
						break;
					case 3:
						bdbs.printAccounts();
						System.out.println("Select ID of account you want to send to");
						accountEntry=scanner.nextInt();
						Account targetAccount=bdbs.getAccountwithID(accountEntry);
						System.out.println("How much would you like to give");
						entry =scanner.nextDouble();
						if(entry>account.ammount)
							throw new BusinessException("Cannot transfer more than ammount in balance");
						else {
							account.transfer(entry, targetAccount);
						}
						
						break;
					case 0:
						break;
					default:
						System.out.println(actionChoice+" Invalid input");
						break;
						
					}	
					}while(actionChoice!=0);
					}
					catch(BusinessException e) {
						System.out.println(e);
					}
					catch (NumberFormatException e) {
						System.out.println(e);
					}
					catch(InputMismatchException e) {
						System.out.println("InputException");
						
					}
				}
				
			}
			catch(NumberFormatException e) {
				
			}
			catch(BusinessException e) {
				
			}
			
			
			break;
		
		case(2):
			System.out.println("What is your initial deposit?");
			
			try {
				double input = scanner.nextDouble();
				try {
				bdbs.requestAccount(input,this.id);
				System.out.println("Awaiting approval by employee");
				}
				catch(BusinessException e){
				
				}
			}
			catch(NumberFormatException e) {
				
			}
			Account a= new Account();
			
			break;
		case (3):
			break;
		default:
			
			System.out.println("Invalid entry");
			break;
		}
		}while(c!=3);
		
		
	}
	
	
	
	
	

}
