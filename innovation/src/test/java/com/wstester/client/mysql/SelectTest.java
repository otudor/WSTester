package com.wstester.client.mysql;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.wstester.mysql.MySQLClient;
import com.wstester.mysql.Result;

public class SelectTest {

	MySQLClient client;
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException{
	
		client = new MySQLClient("localhost", "3306", "test", "appuser", "apppass");
	}
	
	@Test
	public void insertOneRow() throws SQLException, InstantiationException, IllegalAccessException{
		
		String selectStatement = "SELECT * FROM CUSTOMER";
		Result result = client.select(selectStatement);
		
		List<String> columns = result.getColumnsName();
		for(String column : columns){
			System.out.println(column);
			for (Object val : result.getValues(column))
				System.out.println(val);
		}
	}
}
