package com.wstester.dispatcher.mysql;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.wstester.model.MySQLService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Server;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class MysqlConnection {

	@Autowired
	private MysqlDataSource dataSource;
	
	public void setConnection(MySQLStep step) throws Exception{
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		
		MySQLService service =(MySQLService) projectFinder.getServiceById(step.getServiceId());
		Server server = projectFinder.getServerById(step.getServerId());
		int port = Integer.parseInt(service.getPort());
		
		dataSource.setServerName(server.getIp());
		dataSource.setPortNumber(port);
		dataSource.setDatabaseName(service.getDbName());
		dataSource.setUser(service.getUser());
		dataSource.setPassword(service.getPassword());
	}
}
