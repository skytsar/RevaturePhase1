package com.tesar.bank.DAOimpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tesar.bank.Account;
import com.tesar.bank.HelloBank;
import com.tesar.bank.User;
import com.tesar.bank.DAO.BankDAO;
import com.tesar.bank.sql.BankQueries;
import com.tesar.bank.sql.PostresSqlConnection;
import com.tesar.exception.BusinessException;
import org.apache.log4j.Logger;



public class BankDAOimpl implements BankDAO {
	
	private static Logger log=Logger.getLogger(BankDAOimpl.class);
	
	@Override
	public User login(String username, String password)throws BusinessException{
		Connection connection=null;
		User user=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		String sql;
		try {
			connection=PostresSqlConnection.getConnection();
			
			sql=BankQueries.GETUSERLOGIN;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			//Step 4 - Execute Query
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				user = new User(rs.getInt("id"),rs.getString("username"),rs.getString("password"),
						rs.getString("lastname"),rs.getString("firstname"),rs.getString("position"));
				log.trace("User retreived");
			}
			else
			log.trace("Invalid username or password");
			
		} catch (ClassNotFoundException e) {
			log.error("ClassNotFound Error");
			//log.trace(e);
		} catch (SQLException e) {
			log.error("SQL Error");
			//log.trace(e);
		}
		finally {
			try {
			
			connection.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
		return user;
	}
	
	
	@Override
	public void insertNewUser(String username,String password, String firstname,String lastname)throws BusinessException
	{
		Connection connection=null;
		User user=null;
		PreparedStatement preparedStatement=null;
		ResultSet rs=null;
		String sql;
		try {
			connection=PostresSqlConnection.getConnection();
			sql=BankQueries.INSERTNEWUSER;
			preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setInt(1, default);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, lastname);
			preparedStatement.setString(4, firstname);
			//preparedStatement.setString(6, "c");
			preparedStatement.executeUpdate();
			
			log.trace("New user inserted");
	} catch (ClassNotFoundException e) {
		log.trace(e);} 
	catch (SQLException e) {
		log.error("Error: SQL");
		throw new BusinessException("This username is already in use");
		
		//log.trace(e);
	}
	finally {
		try {
		
		connection.close();
		
			//log.trace("Connection closed");
		} catch (SQLException e) {
			log.trace(e);
		}
	}
	}
	
	@Override
	public Account getAccountwithID(int accountId) throws BusinessException{
		Connection connection =null;
		Account account=new Account();
		try {
			
			connection=PostresSqlConnection.getConnection();
			//log.trace("Connection Successfull");
			//Step 3 - Create Statement
			//Statement statement=connection.createStatement();
			String sql=BankQueries.GETACCOUNTWITHID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountId);
			
			//Step 4 - Execute Query
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next())
			account =new Account(accountId,rs.getDouble("balance"),rs.getInt("owner_id"));
			else
				throw new BusinessException("Account doesn't exist");
		} catch (ClassNotFoundException e) {
			log.trace(e);
		} catch (SQLException e) {
			log.trace(e);
		}
		finally {
			try {
				//Step 6 - Close Connection
			log.trace("Account retrieved");
			connection.close();
				//log.trace("Connection closed");
			//return account;
				
			} catch (SQLException e) {
				log.trace(e);
			}
		}
		
		return account;
	}
	
	@Override
	public void updateAccount(Account account)throws BusinessException{
		Connection connection =null;
		try {
			//Step 1 - Load/Register the Driver
			//Step 2 - Open Connection(url,username,password)
			connection=PostresSqlConnection.getConnection();
			//log.trace("Connection Successfull");
			//Step 3 - Create Statement
			//Statement statement=connection.createStatement();
			String sql=BankQueries.UPDATEACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, account.ammount);
			preparedStatement.setInt(2, account.accountId);
			//Step 4 - Execute Query
			preparedStatement.executeUpdate();
			//ResultSet rs=statement.executeQuery(sql);
			//log.trace("Query Executed");
			//Step 5 - Process Results
		} catch (ClassNotFoundException e) {
			log.trace(e);
		} catch (SQLException e) {
			log.trace(e);
		}
		finally {
			try {
				//Step 6 - Close Connection\
				log.trace("Account updated");
			connection.close();
				//log.trace("Connection closed");
				
			} catch (SQLException e) {
				log.trace(e);
			}
		}
	}
	
	@Override
	public void insertAccount(double cash, int owner)throws BusinessException{
		Connection connection =null;
		Account account=new Account();
		BigDecimal bdCash= null;
		if(cash<0) {
			throw new BusinessException("Cannot have negative initial deposit");
		}
		else {
		cash=account.validMoney(cash);
		bdCash=new BigDecimal(cash);
		try {
			
			connection=PostresSqlConnection.getConnection();
			
			String sql=BankQueries.INSERTACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setInt(1, this.accountId);
			preparedStatement.setBigDecimal(1, bdCash);
			preparedStatement.setInt(2, owner);
			//Step 4 - Execute Query
			preparedStatement.executeUpdate();
			
			
			
			//ResultSet rs=statement.executeQuery(sql);
			//log.trace("Query Executed");
			//Step 5 - Process Results
		} catch (ClassNotFoundException e) {
			log.trace(e);
		} catch (SQLException e) {
			log.trace(e);
		}
		finally {
			try {
				log.trace("New account inserted");
			connection.close();
				//log.trace("Connection closed");
			
				
			} catch (SQLException e) {
				log.trace(e);
			}
		}
		}
		
	}
	
	
	@Override
	public List<Account> getPendingAccounts()throws BusinessException{
		List<Account> accountList = new ArrayList();
		Connection connection=null;
		try  {
			connection = PostresSqlConnection.getConnection();
			String sql = BankQueries.GETALLPENDINGACCOUNTS;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setInt(1,ownerid);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Account account = new Account(rs.getInt("account_id"),rs.getDouble("balance"),rs.getInt("owner_id"));
				accountList.add(account);
			}
			/*if(playerList.size()==0)
			{
				throw new BusinessException("No Player Records Available");
			}*/
		} catch (ClassNotFoundException  e) {
			log.trace(e); // take off this line when in production
			
		} catch (SQLException e) {
			log.trace(e);
		}
		log.trace("List of pending accounts retrieved");
		return accountList;
		
	}
	
	@Override
	public List<Account> getAccountsWithID(int ownerid)throws BusinessException{
		List<Account> accountList = new ArrayList();
		Connection connection=null;
		try  {
			connection = PostresSqlConnection.getConnection();
			String sql = BankQueries.GETACCOUNTSWITHOWNER;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,ownerid);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Account account = new Account(rs.getInt("account_id"),rs.getDouble("balance"),ownerid);
				accountList.add(account);
			}
			/*if(playerList.size()==0)
			{
				throw new BusinessException("No Player Records Available");
			}*/
		} catch (ClassNotFoundException  e) {
			log.trace(e); // take off this line when in production
			
		} catch (SQLException e) {
			log.trace(e);
		}
		log.trace("List of Customer's accounts retrieved");
		return accountList;
		
	}
	@Override
	public void deletePendingAccount(int account_id) throws BusinessException {
		Connection connection =null;
		try  {
			connection = PostresSqlConnection.getConnection();
			String sql = BankQueries.DELETEPENDINGACCOUNTWITHID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,account_id);
			preparedStatement.executeUpdate();
			
			/*if(playerList.size()==0)
			{
				throw new BusinessException("No Player Records Available");
			}*/
		} catch (ClassNotFoundException  e) {
			log.trace(e); // take off this line when in production
			
		} catch (SQLException e) {
			log.trace(e);
		}
		log.trace("Pending account resolved");
	}
	
	
	@Override
	public void requestAccount(double cash, int owner)throws BusinessException{
		Connection connection =null;
		Account account=new Account();
		BigDecimal bdCash= null;
		if(cash<0) {
			throw new BusinessException("Cannot have negative initial deposit");
		}
		else {
		cash=account.validMoney(cash);
		bdCash=new BigDecimal(cash);
		try {
			
			connection=PostresSqlConnection.getConnection();
			
			String sql=BankQueries.INSERTPENDINGACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setInt(1, this.accountId);
			preparedStatement.setBigDecimal(1, bdCash);
			preparedStatement.setInt(2, owner);
			//Step 4 - Execute Query
			preparedStatement.executeUpdate();
			
			
			
			//ResultSet rs=statement.executeQuery(sql);
			//log.trace("Query Executed");
			//Step 5 - Process Results
		} catch (ClassNotFoundException e) {
			log.trace(e);
		} catch (SQLException e) {
			log.trace(e);
		}
		finally {
			try {
				//Step 6 - Close Connection
			log.trace("New account requested");
			connection.close();
				//log.trace("Connection closed");
			
				
			} catch (SQLException e) {
				log.trace(e);
			}
		}
		}
		
	}
	@Override
	public void insertTransfer(int sourceId, int target,double cash) throws BusinessException{
		Connection connection =null;
		Account account=new Account();
		BigDecimal bdCash= null;
		if(cash<0) {
			throw new BusinessException("Cannot have negative initial deposit");
		}
		else {
		cash=account.validMoney(cash);
		bdCash=new BigDecimal(cash);
		try {
			connection=PostresSqlConnection.getConnection();
			String sql=BankQueries.INSERTTRANSACTION;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setInt(1, this.accountId);
			preparedStatement.setInt(1, sourceId);
			preparedStatement.setString(2, "transfer");
			preparedStatement.setInt(3, target);
			preparedStatement.setBigDecimal(4, bdCash);
			//Step 4 - Execute Query
			preparedStatement.executeUpdate();
			//ResultSet rs=statement.executeQuery(sql);
			//log.trace("Query Executed");
			//Step 5 - Process Results
		} catch (ClassNotFoundException e) {
			log.trace(e);
		} catch (SQLException e) {
			log.trace(e);
		}
		finally {
			try {
			log.trace("Insert transfer from account #"+sourceId+" to account #"+target);
			connection.close();
			} catch (SQLException e) {
				log.trace(e);
			}
		}
		}
		
	}
	@Override
	public void insertTransaction(int sourceId,String type,double cash) throws BusinessException{
		Connection connection =null;
		Account account=new Account();
		BigDecimal bdCash= null;
		if(cash<0) {
			throw new BusinessException("Cannot have negative initial deposit");
		}
		else {
		cash=account.validMoney(cash);
		bdCash=new BigDecimal(cash);
		try {
			connection=PostresSqlConnection.getConnection();
			String sql=BankQueries.INSERTTRANSACTION;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setInt(1, this.accountId);
			preparedStatement.setInt(1, sourceId);
			preparedStatement.setString(2, type);
			preparedStatement.setNull(3, java.sql.Types.INTEGER);
			preparedStatement.setBigDecimal(4, bdCash);
			//Step 4 - Execute Query
			preparedStatement.executeUpdate();
			//ResultSet rs=statement.executeQuery(sql);
			//log.trace("Query Executed");
			//Step 5 - Process Results
		} catch (ClassNotFoundException e) {
			log.trace(e);
		} catch (SQLException e) {
			log.trace(e);
		}
		finally {
			try {
			log.trace("Account #"+sourceId+" requested a "+type);
			connection.close();
			} catch (SQLException e) {
				log.trace(e);
			}
		}
		}
		
	}
	@Override
	public void printTransactions()throws BusinessException{
		Connection connection =null;
		try {
			
			connection=PostresSqlConnection.getConnection();
			
			String sql=BankQueries.GETTRANSACTIONS;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setInt(1, this.accountId);
			
			//Step 4 - Execute Query
			ResultSet rs =preparedStatement.executeQuery();
			log.trace("Id   Source ID   Transaction type     Target Id     Ammount         Time");
			while(rs.next()) {
				log.trace(rs.getInt("id")+"       "+rs.getInt("source_account")+"          "+
			rs.getString("transaction_type")+"               "+rs.getInt("target_account")+"          "+
						rs.getDouble("ammount")+"          "+rs.getTime("time"));
				
			}
		} catch (ClassNotFoundException e) {
			log.trace(e);
		} catch (SQLException e) {
			log.trace(e);
		}
		finally {
			try {
				//Step 6 - Close Connection
			log.trace("Transactions printed");
			connection.close();
				//log.trace("Connection closed");
			
				
			} catch (SQLException e) {
				log.trace(e);
			}
		}
		}
		
	@Override
	public void printAccounts()throws BusinessException{
		Connection connection =null;
		try {
			
			connection=PostresSqlConnection.getConnection();
			
			String sql=BankQueries.GETALLACCOUNTS;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.setInt(1, this.accountId);
			
			//Step 4 - Execute Query
			ResultSet rs =preparedStatement.executeQuery();
			log.trace("Account ID             Balance               Owner ID");
			while(rs.next()) {
				log.trace(rs.getInt("account_id")+"              "+rs.getDouble("balance")+"                 "+
			rs.getString("owner_id"));
				
			}
		} catch (ClassNotFoundException e) {
			log.trace(e);
		} catch (SQLException e) {
			log.trace(e);
		}
		finally {
			try {
			log.trace("Accounts printed");
			connection.close();
				//log.trace("Connection closed");
			
				
			} catch (SQLException e) {
				log.trace(e);
			}
		}
		}
@Override
public void printCustomers()throws BusinessException{
		Connection connection =null;
		try {
			connection=PostresSqlConnection.getConnection();
			
			String sql=BankQueries.GETPUBLICUSERS;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet rs =preparedStatement.executeQuery();
			log.trace("ID     username      Lastname        Firstname");
			while(rs.next())
			log.trace(rs.getInt("id")+"      "+rs.getString("username")+"        "
			+rs.getString("lastname")+"        "+rs.getString("firstname"));
			
		}
		catch (ClassNotFoundException e) {
			log.trace(e);
		} catch (SQLException e) {
			log.trace(e);
		}
		finally {
			try {
			log.trace("Customer list printed");
			connection.close();
				//log.trace("Connection closed");
			
				
			} catch (SQLException e) {
				log.trace(e);
			}
		}
	}
	

}
