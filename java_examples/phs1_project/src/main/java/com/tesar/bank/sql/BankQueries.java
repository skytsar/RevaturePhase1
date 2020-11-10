package com.tesar.bank.sql;

public class BankQueries {
	public static final String GETUSERLOGIN = "select Id,username,password,lastname,firstname,position from project.user where USERNAME=? AND PASSWORD=?";
	public static final String GETMAXUSERID ="select max(id) from project.user";
	public static final String CHECKIFUSERNAMEUSED = "select id from project.user where username=?";
	public static final String INSERTNEWUSER = "insert into project.user (id,username,password,lastname,firstname,position) values(default,?,?,?,?,'c')";
	public static final String UPDATEACCOUNT = "update project.accounts set balance=? where account_id = ?";
	public static final String INSERTACCOUNT = "insert into project.accounts (account_id,balance,owner_id) values(default,?,?)";
	public static final String GETALLACCOUNTS = "select account_id, balance, owner_id from project.accounts";
	public static final String GETACCOUNTSWITHOWNER = "select account_id, balance from project.accounts where owner_id = ?";
	public static final String GETACCOUNTWITHID = "select balance, owner_id from project.accounts where account_id=?";
	public static final String DELETEACCOUNTWITHID = "delete from project.accounts where account_id=?"; 
	public static final String DELETEUSERWITHID = "delete from project.user where id=?"; 
	public static final String GETPUBLICUSERS = "select id, username, password, lastname,firstname, position from project.user where position='c'";
	public static final String GETALLPENDINGACCOUNTS = "select account_id, balance, owner_id from project.pending_accounts";
	public static final String DELETEPENDINGACCOUNTWITHID = "delete from project.pending_accounts where account_id=?";
	public static final String INSERTPENDINGACCOUNT = "insert into project.pending_accounts (account_id,balance,owner_id) values(default,?,?)";
	public static final String INSERTTRANSACTION="insert into project.transactions (id,source_account,transaction_type,target_account,ammount,time) values(default,?,?,?,?,current_timestamp)";
	public static final String GETTRANSACTIONS= "select id,source_account,transaction_type,target_account,ammount,time from project.transactions";
}
