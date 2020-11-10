package com.tesar.bank.DAOimpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tesar.bank.Account;
import com.tesar.bank.User;
import com.tesar.bank.DAO.BankDAO;
import com.tesar.bank.sql.BankQueries;
import com.tesar.bank.sql.PostresSqlConnection;
import com.tesar.exception.BusinessException;




public class BankDAOimpl implements BankDAO {
	//private BankDAO bankDAO = new BankDAOimpl();
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
				
			}
			else
			System.out.println("Invalid username or password");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			try {
				
			connection.close();
			} catch (SQLException e) {
				System.out.println(e);
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
			
	} catch (ClassNotFoundException e) {
		System.out.println(e);} 
	catch (SQLException e) {
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
	
	@Override
	public Account getAccountwithID(int accountId) throws BusinessException{
		Connection connection =null;
		Account account=new Account();
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
			if(rs.next())
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
		
		return account;
	}
	
	@Override
	public void updateAccount(Account account)throws BusinessException{
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
			preparedStatement.setDouble(1, account.ammount);
			preparedStatement.setInt(2, account.accountId);
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
			System.out.println(e); // take off this line when in production
			
		} catch (SQLException e) {
			System.out.println(e);
		}
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
			System.out.println(e); // take off this line when in production
			
		} catch (SQLException e) {
			System.out.println(e);
		}
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
			System.out.println(e); // take off this line when in production
			
		} catch (SQLException e) {
			System.out.println(e);
		}
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
			//System.out.println("Query Executed");
			//Step 5 - Process Results
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			try {
			connection.close();
			} catch (SQLException e) {
				System.out.println(e);
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
			//System.out.println("Query Executed");
			//Step 5 - Process Results
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			try {
			connection.close();
			} catch (SQLException e) {
				System.out.println(e);
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
			System.out.println("Id     Source ID     Transaction type        Target Id         Ammount      Time");
			while(rs.next()) {
				System.out.println(rs.getInt("id")+"       "+rs.getInt("source_account")+"          "+
			rs.getString("transaction_type")+"        "+rs.getInt("target_account")+"          "+
						rs.getDouble("ammount")+"          "+rs.getTime("time"));
				
			}
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
			System.out.println("Account ID             Balance               Owner ID");
			while(rs.next()) {
				System.out.println(rs.getInt("account_id")+"       "+rs.getDouble("balance")+"          "+
			rs.getString("owner_id"));
				
			}
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
