package com.tesar.bank.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostresSqlConnection {

	private static Connection connection;
	
	private PostresSqlConnection() {
		// TODO Auto-generated constructor stub
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DbUtil.DRIVER);
		String url=DbUtil.URL;			
		String username="postgres";
		String password="Bacon123";
		connection=DriverManager.getConnection(url, username, password);
		return connection;
	}
}
//Single Ton - Single TON Java CLass.