package com.tesar.bank;

import java.sql.SQLException;

import com.tesar.bank.sql.BankQueries;
import com.tesar.bank.sql.PostresSqlConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.System;

public class Account {
	public int accountId;
	public double ammount;
	public int ownerId;
	
	public double validMoney(double input) {
		int temp = (int)(input*100.0);
	    double value = ((double)temp)/100.0;
	    return value;
	}
	
	public Account(int id,double cash, int owner) {
		accountId =id;
		ammount=cash;
		ownerId=owner;
	}
	public Account() {
		accountId =0;
		ammount=0;
		ownerId=0;
	}
	

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getAmmount() {
		return ammount;
	}

	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	public void showAccount() {
		
	}
	
	public void deposit(double deposit) {
		deposit=this.validMoney(deposit);
		setAmmount(this.ammount+deposit);
		this.update();
	}
	public void withdrawl(double withdrawl) {
		withdrawl=this.validMoney(withdrawl);
		setAmmount(this.ammount-withdrawl);
		this.update();
	}
	public void transfer(double ammount, Account target) {
		this.withdrawl(ammount);
		target.deposit(ammount);
	}
	public  Account(int accountId) {
		Connection connection =null;
		Account account=null;
		try {
			
			connection=PostresSqlConnection.getConnection();
			//System.out.println("Connection Successfull");
			//Step 3 - Create Statement
			//Statement statement=connection.createStatement();
			String sql=BankQueries.GETACCOUNTWITHID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountId);
			
			//Step 4 - Execute Query
			ResultSet rs=preparedStatement.executeQuery();
			account =new Account(accountId,rs.getDouble("balance"),rs.getInt("owner_id"));
			
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			try {
				//Step 6 - Close Connection
			connection.close();
				//System.out.println("Connection closed");
			//return account;
				
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		
		
	}
	
	public void update() {
		Connection connection =null;
		try {
			//Step 1 - Load/Register the Driver
			//Step 2 - Open Connection(url,username,password)
			connection=PostresSqlConnection.getConnection();
			//System.out.println("Connection Successfull");
			//Step 3 - Create Statement
			//Statement statement=connection.createStatement();
			String sql=BankQueries.UPDATEACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, this.ammount);
			preparedStatement.setInt(2, this.accountId);
			//Step 4 - Execute Query
			preparedStatement.executeQuery();
			//ResultSet rs=statement.executeQuery(sql);
			//System.out.println("Query Executed");
			//Step 5 - Process Results
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			try {
				//Step 6 - Close Connection
			connection.close();
				//System.out.println("Connection closed");
				
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		
		
	}
	public void insertAccount(double cash,int owner) {
		Connection connection =null;
		if(cash<0) {
			System.out.println("Cannot have negative initial deposite");
		}
		else {
		cash=this.validMoney(cash);
		try {
			//Step 1 - Load/Register the Driver
			//Step 2 - Open Connection(url,username,password)
			connection=PostresSqlConnection.getConnection();
			//System.out.println("Connection Successfull");
			//Step 3 - Create Statement
			//Statement statement=connection.createStatement();
			String sql=BankQueries.INSERTACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setInt(1, this.accountId);
			preparedStatement.setDouble(1, cash);
			preparedStatement.setInt(2, owner);
			//Step 4 - Execute Query
			preparedStatement.executeUpdate();
			
			
			
			//ResultSet rs=statement.executeQuery(sql);
			//System.out.println("Query Executed");
			//Step 5 - Process Results
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			try {
				//Step 6 - Close Connection
			connection.close();
				//System.out.println("Connection closed");
			
				
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		}
		
	}
	
	
	
}
