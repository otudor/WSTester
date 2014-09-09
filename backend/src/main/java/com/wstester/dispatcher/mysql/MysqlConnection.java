package com.wstester.dispatcher.mysql;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.wstester.model.MySQLService;
import com.wstester.model.MySQLStep;

public class MysqlConnection {

	@Autowired
	private MysqlDataSource dataSource;
	
	public void setConnection(MySQLStep step){
		
		MySQLService service =(MySQLService) step.getService();
		int port = Integer.parseInt(service.getPort());
		
		dataSource.setServerName(step.getServer().getIp());
		dataSource.setPortNumber(port);
		dataSource.setDatabaseName(service.getDbName());
		dataSource.setUser(service.getUser());
		dataSource.setPassword(service.getPassword());
	}
}
