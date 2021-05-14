package com;

import java.sql.*;

public class User {
	
	private Connection connect() 
	 { 
	 Connection con = null; 
	 try
	 { 
	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	 
	 //Provide the correct details: DBServer/DBName, username, password 
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/buyeardb", "root", ""); 
	 } 
	 catch (Exception e) 
	 { 
		 e.printStackTrace();
	 } 
	 return con; 
	 } 
	
	public String insertUser(String Email, String Name, String Address, String Contact) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for inserting."; } 
	 // create a prepared statement
	 String query = " insert into item (`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"
	 + " values (?, ?, ?, ?, ?)"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(2, Email); 
	 preparedStmt.setString(3, Name); 
	 preparedStmt.setDouble(4, Double.parseDouble(Address)); 
	 preparedStmt.setString(5, Contact); 
	 
	// execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 
	 	String newItems = readUser();
	 	output = "{\"status\":\"success\", \"data\": \"" + 
	 			 newItems + "\"}"; 
	 } 
	 catch (Exception e) 
	 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	public String readUser() 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for reading."; } 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>User Email</th><th>User Name</th>" +
	 "<th>User Address</th>" + 
	 "<th>User Contact</th>" +
	 "<th>Update</th><th>Remove</th></tr>"; 
	 
	 String query = "select * from buyeardb"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String ID = Integer.toString(rs.getInt("ID")); 
	 String Email = rs.getString("Email"); 
	 String Name = rs.getString("Name"); 
	 String Address = Double.toString(rs.getDouble("Address")); 
	 String Contact = rs.getString("Contact"); 
	 
	// Add into the html table
	 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + ID
	 + "'>" + Email + "</td>"; 
	 output += "<td>" + Name + "</td>"; 
	 output += "<td>" + Address + "</td>"; 
	 output += "<td>" + Contact + "</td>"; 
	 
	 
	// buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
			+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='"
			+ ID + "'>" + "</td></tr>"; 
	 } 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while reading the user."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	public String updateUser(String ID, String Email, String Name, String Address, String Contact)
	{ 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for updating."; } 
		 // create a prepared statement
		 String query = "UPDATE item SET userEmail=?,userName=?,userAddress=?,userContact=? WHERE userID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 // binding values
		 preparedStmt.setString(1, Email); 
		 preparedStmt.setString(2, Name); 
		 preparedStmt.setDouble(3, Double.parseDouble(Address)); 
		 preparedStmt.setString(4, Contact); 
		 preparedStmt.setInt(5, Integer.parseInt(ID)); 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 String newUser = readUser();
		 output = "{\"status\":\"success\", \"data\": \"" + 
				 newUser + "\"}";  
		 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the user.\"}"; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 } 
	
	public String deleteUser(String userID) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {
		 return "Error while connecting to the database for deleting."; 
	 } 
	 // create a prepared statement
	 String query = "delete from item where userID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(userID)); 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close();
	 
	 String newUser = readUser();
	 output = "{\"status\":\"success\", \"data\": \"" + 
			 newUser + "\"}"; 
	 } 
	 catch (Exception e) 
	 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while deleting the user.\"}"; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 


}
