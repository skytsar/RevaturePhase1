package com.tesar.bank;

import java.sql.SQLException;


import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import java.lang.System;
import com.tesar.bank.DAO.*;
import com.tesar.bank.DAOimpl.*;
import com.tesar.exception.BusinessException;
import java.math.BigDecimal;

public class Account {
	public int accountId;
	public double ammount;
	public int ownerId;
	private static Logger log=Logger.getLogger(Account.class);
	
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
		this.insertTransaction("deposit", deposit);
		this.update();
	}
	public void withdrawl(double withdrawl) {
		withdrawl=this.validMoney(withdrawl);
		setAmmount(this.ammount-withdrawl);
		this.insertTransaction("withdrawl", withdrawl);
		this.update();
	}
	public void transfer(double ammount, Account target) {
		this.setAmmount(this.ammount-ammount);
		target.setAmmount(ammount+target.ammount);
		this.insertTransfer(target.accountId, ammount);
		this.update();
		target.update();
	}
	public void insertTransaction(String type, double ammount) {
		BankDAO bdbs=new BankDAOimpl();
		try {
			bdbs.insertTransaction(this.accountId, type, ammount);
		}
		catch(BusinessException e) {
			log.trace(e);
		
		}
		
	}
	public void insertTransfer(int targetID,double ammount) {
		BankDAO bdbs=new BankDAOimpl();
		try {
			bdbs.insertTransfer(this.accountId, targetID, ammount);
		}
		catch(BusinessException e) {
			log.trace(e);
		
		}
	}
	
	
	public void update() {
		
		BankDAO bdbs=new BankDAOimpl();
		try {
			bdbs.updateAccount(this);
		}
		catch(BusinessException e) {
			log.trace(e);
		
		}
		
		
	}
	
		
	
	
	
}
