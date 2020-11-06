package com.tesar.bank;


public class Account {
	public int accountId;
	public double ammount;
	public int ownerId;
	
	public Account(int id,double cash, int owner) {
		accountId =id;
		ammount=cash;
		ownerId=owner;
	}
	public void withdrawl(double removal) {
		
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
	
	
	
}
