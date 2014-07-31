package com.wstester.client.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wstester.client.Client;

public class MySQLClient extends Client{

	private Connection conn;
	
	public MySQLClient(String hostname, String port, String dbName, String user, String pass) throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + dbName, user, pass);
	}
	
	public Result select(String selectStatement) throws SQLException{
	
		Statement statemenet = conn.createStatement();
		ResultSet resultset = statemenet.executeQuery(selectStatement);
		
		Result result = new Result(resultset);
		
		return result;
	}
	
	public int insert(String insertStatement) throws SQLException {
		
		Statement statemenet = conn.createStatement();
		int rowsInserted = statemenet.executeUpdate(insertStatement);
		
		return rowsInserted;
	}
	
	public void close() throws SQLException{
		
		this.conn.close();
	}
}
