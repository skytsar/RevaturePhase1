package com.tesar.bank.DAO;
import java.util.List;

import com.tesar.exception.BusinessException;

import com.tesar.bank.User;
import com.tesar.bank.Account;


public interface BankDAO {
	public User login(String username, String password)throws BusinessException;
	public void insertNewUser(String username,String password, String firstname,String lastname)throws BusinessException;
	public Account getAccountwithID(int accountId) throws BusinessException;
	public void updateAccount(Account account)throws BusinessException;
	public void insertAccount(double cash, int owner)throws BusinessException;
	public List<Account> getAccountsWithID(int ownerid)throws BusinessException;
	public void requestAccount(double cash, int owner) throws BusinessException;
	public List<Account> getPendingAccounts() throws BusinessException;
	public void deletePendingAccount(int account_id) throws BusinessException;
	public void insertTransfer(int sourceId,int target, double cash) throws BusinessException;
	public void insertTransaction(int sourceId,String type,double cash) throws BusinessException;
	public void printTransactions()throws BusinessException;
	public void printAccounts()throws BusinessException;
	public void printCustomers()throws BusinessException;

}
