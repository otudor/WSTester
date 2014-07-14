package com.wstester.client.mysql;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wstester.mysql.MySQLClient;

public class InsertTest {

	static MySQLClient client;
	
	@BeforeClass
	public static void setUp() throws ClassNotFoundException, SQLException{
	
		client = new MySQLClient("localhost", "3306", "test", "appuser", "apppass");
	}
	
	@Test
	public void insertOneRow() throws SQLException{
		
		String insertStatement = "INSERT INTO CUSTOMER(NAME) VALUES('AnotherPan')";
		int rowsInserted = client.insert(insertStatement);
		assertEquals(1,rowsInserted);
	}
	
	@AfterClass
	public static void tear() throws SQLException{
		
		client.close();
	}
}
