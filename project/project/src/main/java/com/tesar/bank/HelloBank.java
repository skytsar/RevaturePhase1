package com.tesar.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.System;


import com.tesar.bank.DAO.*;
import com.tesar.bank.DAOimpl.*;
import com.tesar.bank.sql.BankQueries;
import com.tesar.bank.sql.PostresSqlConnection;
import com.tesar.exception.BusinessException;
import java.util.List;
import java.util.Scanner;
//import User.java;

import org.apache.log4j.Logger;

public class HelloBank {
	
private static Logger log=Logger.getLogger(HelloBank.class);
	public static void main(String[] args) {
		
		
		String username;
		String password;
		String firstname;
		String lastname;
		
		BankDAO bdbs=new BankDAOimpl();
		User user=null;
		Scanner scanner = new Scanner(System.in);
		log.trace("Welcome to Tesar's Bank app");
		log.trace("--------------------------------------------------");
		
		int ch = 0;
		do {
			log.trace("Enter 1 in order to log in, 2 to make a new account enter 3 to exit");
			try {
				ch = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {

			}
			switch(ch){
			case(1):
				log.trace("Enter Username");
				username = scanner.nextLine();
				log.trace("Enter password");
				password= scanner.nextLine();
				try {
					user =bdbs.login(username,password);
				}
				catch(BusinessException e) {
					log.error(e);
					
				}
				if(user!=null) {
					//log.trace("Account #"+user.id+" has logged in");
						if(user.position=='c')
							user.customerOptions();
						else if(user.position=='e')
							user.employeeOptions();
						else
							log.trace("Invalid position");
						log.trace("Logging out");
				}
				
					
				break;
				
			case(2):
				log.trace("Enter Username");
				username = scanner.nextLine();
				log.trace("Enter password");
				password= scanner.nextLine();
				log.trace("Enter first name");
				firstname= scanner.nextLine();
				log.trace("Enter Last name");
				lastname=scanner.nextLine();
				try {
				
				bdbs.insertNewUser(username, password, firstname, lastname);
				user =bdbs.login(username, password);
				//log.trace("New account created. Account #"+user.id);
				//log.trace("Account #"+user.id+" has logged in");
				user.customerOptions();
				}
				catch(BusinessException e) {
					log.error(e);
					
				}
				catch(NullPointerException e) {
					log.error("Error: nullpointer");
				}
			break;
			case 3:
				break;
			
			default: log.warn("Invalid");
				break;
			
			}
			
			
		} while (ch != 3);
		log.trace("Exiting program");

	}

}
