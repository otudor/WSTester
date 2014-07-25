package com.wstester.client.mysql;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wstester.client.mysql.MySQLClient;
import com.wstester.client.mysql.Result;

public class SelectTest {

	static MySQLClient client;
	
	@BeforeClass
	public static void setUp() throws ClassNotFoundException, SQLException{
	
		client = new MySQLClient("localhost", "3306", "test", "appuser", "apppass");
	}
	
	@Test
	public void selectAll() throws SQLException, InstantiationException, IllegalAccessException{
		
		String selectStatement = "SELECT * FROM CUSTOMER";
		Result result = client.select(selectStatement);
		
		List<String> columns = result.getColumnsName();
		for(String column : columns){
			System.out.println(column);
			for (Object val : result.getValues(column))
				System.out.println(val);
		}
		
		result.close();
	}
	
	@AfterClass
	public static void tear() throws SQLException{
		
		client.close();
	}
}
