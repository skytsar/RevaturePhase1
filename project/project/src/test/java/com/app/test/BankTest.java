package com.app.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.tesar.bank.DAO.*;
import com.tesar.bank.DAOimpl.*;
import com.tesar.bank.sql.PostresSqlConnection;
import com.tesar.exception.BusinessException;
import java.sql.SQLException;
import com.tesar.bank.User;
import com.tesar.bank.Account;
import java.util.ArrayList;
import java.util.List;

public class BankTest {

	private static BankDAO bdbs;
	@BeforeAll
	public static void bankDAOsetup() {
		bdbs=new BankDAOimpl();
		
	}
	
	
	
	
	@Test
	public void testRejectUsedUsername() {
		boolean rejected=false;
		try {
			bdbs.insertNewUser("a", "rest", "don't", "matter");
			
		}
		catch(BusinessException e) {
			rejected=true;
		}
		
		finally {
			assertTrue(rejected);
		}
	}
	
	@Test
	public void blockIllegalAccountRequest() {
		boolean rejected=false;
		try {
			Account a=bdbs.getAccountwithID(-1);
		}
		catch(BusinessException e) {
			rejected=true;
		}
		
		finally {
			assertTrue(rejected);
		}
		
	}
	@Test
	public void getLegalAccount() {
		boolean rejected=false;
		try {
			Account a=bdbs.getAccountwithID(1);
			rejected=true;
		}
		catch(BusinessException e) {
			rejected=false;
		}
		
		finally {
			assertTrue(rejected);
		}
		
	}
	@Test
	public void login() {
		boolean success = true;
		try {
			User user =bdbs.login("a", "b");
		}
		catch(BusinessException e) {
			success=false;
		}
	}
	@Test
	public void getRequestedAccountsWorks() {
		boolean success;
		try {
			List<Account> a=bdbs.getPendingAccounts();
			success=true;
		}
		catch(BusinessException e) {
			success=false;
		}
		assertTrue(success);
	}
	@Test
	public void printTransactions() {
		boolean success;
		try {
			bdbs.printTransactions();
			success=true;
		}
		catch(BusinessException e) {
			success=false;
		}
		assertTrue(success);
	}
	@Test
	public void printAccounts() {
		boolean success;
		try {
			bdbs.printAccounts();
			success=true;
		}
		catch(BusinessException e) {
			success=false;
		}
		assertTrue(success);
	}
	
	@Test
	public void printCustomers() {
		boolean success;
		try {
			bdbs.printCustomers();
			success=true;
		}
		catch(BusinessException e) {
			success=false;
		}
		assertTrue(success);
	}
	@Test
	public void returnsAccountListGivenOwner() {
		boolean success=false;
		try {
			List<Account> al = bdbs.getAccountsWithID(102);
			success=true;
		}
		catch(BusinessException e) {
			success=false;
		}
		
		finally {
			assertTrue(success);
		}
		
	}
	@Test
	public void retunsEmptyListWhenGivenInvalidAccount() {
		boolean success=false;
		try {
			List<Account> al = bdbs.getAccountsWithID(-1);
			if(al.isEmpty())
			success=true;
		}
		catch(BusinessException e) {
			success=false;
		}
		
		finally {
			assertTrue(success);
		}
		
	}
	/*@AfterEach
	public void helloAfterEach() {
		System.out.println("hello from @AfterEach, this will be executed after every Test case in this class");
	}
	
	@AfterAll
	public static void helloAfterAll() {
		System.out.println("Hello from @AfterAll");
	}*/
}
