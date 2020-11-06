package com.tesar.bank;

public class User {
	public int id;
	public String username;
	private String password;
	public String firstName;
	public String lastName;
	public char position;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User(int id, String uname, String pword, String lname,String fname,String position) {
		super();
		this.id=id;
		this.username=uname;
		this.password=pword;
		this.firstName=fname;
		this.lastName=lname;
		this.position=position.toCharArray()[0];
		
		
	}
	public void hello() {
		System.out.println("Hello "+firstName);
	}
	

}
