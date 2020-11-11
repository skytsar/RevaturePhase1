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

import org.apache.log4j.Logger;

import com.tesar.exception.BusinessException;
import java.util.InputMismatchException;


public class User {
	public int id;
	public String username;
	private String password;
	public String firstName;
	public String lastName;
	public char position;
	private static Logger log=Logger.getLogger(User.class);
	
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
		log.trace("Hello "+firstName);
		log.trace("(1) View pending accounts");
		log.trace("(2) view transactions");
		log.trace("(3) view all customers");
		log.trace("(4) Log out");
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
				log.trace("There are no pending account requests");
			else {
			int pendingChoice=-1;
			do {
				log.trace("Account ID        Owner ID            Initial deposit");
			for(Account a:pendingList)
				log.trace(a.accountId+ "                   "+a.ownerId+ "                   "+ a.ammount);
			log.trace("Enter intended account ID (or enter 0 to cancel)");
			int occuranceCount=0;
			
			pendingChoice=Integer.parseInt(scanner.nextLine());
			for(Account a:pendingList) {
				if(a.getAccountId()==pendingChoice) {
					occuranceCount++;
					log.trace("Do you wish do accept this account? y/n");
					String yesno =scanner.next();
					switch(yesno) {
					case("y"):
						bdbs.deletePendingAccount(a.getAccountId());
						bdbs.insertAccount(a.getAmmount(), a.getOwnerId());
						log.trace("Account accepted");
						pendingChoice=0;
						break;
					case("n"):
						bdbs.deletePendingAccount(a.getAccountId());
						log.trace("Account rejected");
						pendingChoice=0;
						break;
					default:
						log.trace("Entry must be either 'y' or 'n'");
						break;
					}
				}
			}
				if (occuranceCount==0)
					log.trace("Number not in pending list");
			}while(pendingChoice!=0);
			}
			}
			catch(BusinessException e){
				
			}
			catch(NumberFormatException e) {
			
			}
			break;
		case(2):
			try {
				bdbs.printTransactions();
			}
			catch(BusinessException e) {
				log.warn("Invalid entry");
			}
			break;
		case(3):
			try {
				bdbs.printCustomers();
				log.trace("Select account number");
				int x=Integer.parseInt(scanner.nextLine());
				List<Account> accountList= bdbs.getAccountsWithID(x);
				if(accountList.isEmpty())
					log.warn("This Customer has no accounts");
				else {
					log.trace("Account ID        Balance");
					for(Account a:accountList)
						log.trace(a.accountId+ "                       "+ a.ammount);
				}
				
			}
			catch(BusinessException e) {
				log.warn(e);
			}
			catch(NumberFormatException e) {
			 log.warn("Invalid entry");
			}
			
			
			break;
		case (4):
			break;
		default:
			log.warn("Invalid entry");
			break;
		}
		}while(c!=4);
		
	}
	
	public void customerOptions() {
		Scanner scanner = new Scanner(System.in);
		BankDAO bdbs=new BankDAOimpl();
		int c = 0;
		do {
			log.trace("Hello "+firstName);
			log.trace("(1) View accounts you own");
			log.trace("(2) Create account");
			log.trace("(3) Log out");
		
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
				log.trace("You have no accounts.");
			}
			else {
				log.trace("Account ID        Balance");
				for(Account a:accountList)
					log.trace(a.accountId+ "                       "+ a.ammount);
				log.trace("Enter intended account");
				//try {
				
					//try {
					
					try {
						//log.trace("In try block");
						 int accountChoice= Integer.parseInt(scanner.nextLine());
						 account=bdbs.getAccountwithID(accountChoice);
						 if(account.ownerId!=this.id) {
							 throw new BusinessException("This is not your account");
						 }
						 
					//} catch (NumberFormatException e) {
						//log.trace(e);
					//}
					
					
					
				
			
				
				
				
				
					int actionChoice=5;
					do {
						log.trace("(0) Return");
						log.trace("(1) Withdraw");
						log.trace("(2) Deposit");
						log.trace("(3) Transfer to another account");
					
					//try {
						//log.trace("In try block");
						 actionChoice= Integer.parseInt(scanner.nextLine());
						 
					//} catch (NumberFormatException e) {
						//log.trace(e);
					//}/
					double entry=0.0;
					int accountEntry;
					
					switch(actionChoice) {
					case 1:
						log.trace("Enter the ammount you want to withdraw");
						entry=Double.parseDouble(scanner.nextLine());
						//entry =scanner.nextDouble();
						if(account.getAmmount()<entry || entry<0.0)
							log.warn("Invalid request");
						else {
							account.withdrawl(entry);
						}
						break;
					case 2:
						log.trace("Enter the ammount you want to deposit");
						entry=Double.parseDouble(scanner.nextLine());
						//entry =scanner.nextDouble();
						
						if(entry<0.0)
							log.warn("Invalid request");
						else {
							account.deposit(entry);
						}
						
						break;
					case 3:
						bdbs.printAccounts();
						log.trace("Select ID of account you want to send to");
						accountEntry=Integer.parseInt(scanner.nextLine());
						Account targetAccount=bdbs.getAccountwithID(accountEntry);
						log.trace("How much would you like to give");
						if(account.accountId==targetAccount.accountId)
							throw new BusinessException("You cannot transfer to the same account");
						entry =Double.parseDouble(scanner.nextLine());
						if(entry>account.ammount)
							throw new BusinessException("Cannot transfer more than ammount in balance");
						else {
							account.transfer(entry, targetAccount);
						}
						
						break;
					case 0:
						break;
					default:
						log.warn(actionChoice+" Invalid input");
						break;
						
					}	
					}while(actionChoice!=0);
					}
					catch(BusinessException e) {
						log.trace(e);
					}
					catch (NumberFormatException e) {
						log.error(e);
					}
					catch(InputMismatchException e) {
						log.error("InputException");
						
					}
				}
				
			}
			catch(NumberFormatException e) {
				
			}
			catch(BusinessException e) {
				
			}
			
			
			break;
		
		case(2):
			log.trace("What is your initial deposit?");
			
			try {
				double input = Double.parseDouble(scanner.nextLine());
				try {
				bdbs.requestAccount(input,this.id);
				log.trace("Awaiting approval by employee");
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
			
			log.warn("Invalid entry");
			break;
		}
		}while(c!=3);
		
		
	}
	
	
	
	
	

}
