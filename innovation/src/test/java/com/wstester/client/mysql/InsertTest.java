package com.wstester.client.mysql;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.wstester.mysql.MySQLClient;

public class InsertTest {

	MySQLClient client;
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException{
	
		client = new MySQLClient("localhost", "3306", "test", "appuser", "apppass");
	}
	
	@Test
	public void insertOneRow() throws SQLException{
		
		String insertStatement = "INSERT INTO CUSTOMER(NAME) VALUES('AnotherPan')";
		int rowsInserted = client.insert(insertStatement);
		assertEquals(1,rowsInserted);
	}
}
