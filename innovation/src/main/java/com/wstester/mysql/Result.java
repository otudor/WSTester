package com.wstester.mysql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Result {

	private List<String> columnsName;
	private ResultSet rs;
	
	public Result(ResultSet rs) throws SQLException{
		this.rs = rs;
		
		ResultSetMetaData rsmd = rs.getMetaData();
		columnsName = new ArrayList<String>();
		for(int i=1; i<=rsmd.getColumnCount(); i++){
			columnsName.add(rsmd.getColumnName(i));
		}
	}
	

	public List<Object> getValues(String column) throws SQLException{
		
		ArrayList<Object> values = new ArrayList<>();
		
		rs.beforeFirst();
		while(rs.next()){
			values.add(rs.getString(column));
		}
		
		return values;
	}
	
	public List<String> getColumnsName() {
		return columnsName;
	}
	
	public void close() throws SQLException{
		
		this.rs.close();
	}
}
