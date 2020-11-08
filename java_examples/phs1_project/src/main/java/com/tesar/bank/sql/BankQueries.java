package com.tesar.bank.sql;

public class BankQueries {
	public static final String GETUSERLOGIN = "select Id,username,password,lastname,firstname,position from project.user where USERNAME=? AND PASSWORD=?";
	public static final String GETMAXUSERID ="select max(id) from project.user";
	public static final String CHECKIFUSERNAMEUSED = "select id from project.user where username=?";
	public static final String INSERTNEWUSER = "insert into project.user (id,username,password,lastname,firstname,position) values(default,?,?,?,?,'c')";
	public static final String UPDATEACCOUNT = "update project.accounts set cash=? where account_id = ?";
	public static final String INSERTACCOUNT = "insert into project.accounts (account_id,balance,owner_id) values(default,?,?)";
	public static final String GETALLACCOUNTS = "select account_id, balance, owner_id from project.accounts";
	public static final String GETACCOUNTSWITHOWNER = "select account_id, balance from project.accounts where owner_id = ?";
	public static final String GETACCOUNTWITHID = "select balance, owner_id from project.accounts where acccount_id=?";
	public static final String DELETEACCOUNTWITHID = "delete from project.accounts where account_id=?"; 
	public static final String DELETEUSERWITHID = "delete from project.user where id=?"; 
	public static final String GETPUBLICUSERS = "select id, username, password, lastname,firstname, position from project.user where position='c'";
}
