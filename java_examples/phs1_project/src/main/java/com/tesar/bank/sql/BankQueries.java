package com.tesar.bank.sql;

public class BankQueries {
	public static final String GETUSERLOGIN = "select Id,username,password,lastname,firstname,position from project.user where USERNAME=? AND PASSWORD=?";

}
