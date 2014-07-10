package com.wstester.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLClient {

	private Connection conn;
	
	public MySQLClient(String hostname, String port, String dbName, String user, String pass) throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + dbName, user, pass);
	}
	
	public Result select(String selectStatement) throws SQLException{
	
		Statement statemenet = conn.createStatement();
		ResultSet resultset = statemenet.executeQuery("SELECT * FROM CUSTOMER");
		
		Result result = new Result(resultset);
		
		return result;
	}
	
	public int insert(String insertStatement) throws SQLException {
		
		Statement statemenet = conn.createStatement();
		int rowsInserted = statemenet.executeUpdate(insertStatement);
		
		return rowsInserted;
	}
}
