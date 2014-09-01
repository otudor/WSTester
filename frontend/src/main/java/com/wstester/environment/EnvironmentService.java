package com.wstester.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TreeItem;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLService;
import com.wstester.model.Server;
import com.wstester.model.Service;

public class EnvironmentService {
	// private long nextId = 0;
	private Map<String, Environment> environments;

	public EnvironmentService() {
		this.environments = new HashMap<String, Environment>();
		// createContact("Cathy", "Freeman");
		Environment e1 = createEnvironment("CT");
		List<Server> list1 = new ArrayList<Server>();
		Server srv1 = new Server("Server1", "44.44.44.44",
				"description ftp server");

		List<Service> lstService = new ArrayList<Service>();
		// MySQL service
		MySQLService mysqlsrv = new MySQLService();
		mysqlsrv.setName("Serviciul de MySQL");
		mysqlsrv.setUser("user");
		mysqlsrv.setDbName("DB Name");
		mysqlsrv.setPort("80");
		mysqlsrv.setPassword("pass");
		lstService.add(mysqlsrv);
		// Mongo service
		MongoService mongosrv = new MongoService();
		mongosrv.setName("Serviciul de Mongo");
		mongosrv.setUser("user");
		mongosrv.setDbName("DB Name");
		mongosrv.setPort("70");
		mongosrv.setPassword("pass");
		lstService.add(mongosrv);
		srv1.setServices(lstService);
		list1.add(srv1);
		e1.setServers(list1);

		Environment e2 = createEnvironment("CHINA Env");
		List<Server> list2 = new ArrayList<Server>();
		list2.add(new Server("Server2", "55.55.55.55",
				"description China server"));
		e2.setServers(list2);

		Environment e3 = createEnvironment("UK Env");
		List<Server> list3 = new ArrayList<Server>();
		list3.add(new Server("Server3", "55.99.80.71", "description UK server"));
		e3.setServers(list3);
	}

	public Environment createEnvironment(String name) {
		Environment env = new Environment(name);
		environments.put(env.getID(), env);
		return env;
	}

	public List<Environment> loadEnvironments() {
		List<Environment> matches = new ArrayList<Environment>();
		matches.addAll(environments.values());

		return matches;
	}

	public Environment getEnvironment(String uID) {
		return environments.get(uID);
	}

	public Environment getFirstEnv() {
		Environment result = null;
		if (!environments.isEmpty())
			result = environments.get(environments.keySet().toArray()[0]);

		return result;
	}

	public void removeServer(String envUID, String serverUID) {
		Environment env = environments.get(envUID);

		if (env != null) {
			List<Server> serverList = env.getServers();
			if (serverList != null && !serverList.isEmpty())
				for (Server server : serverList)
					if (server.getID() == serverUID) {
						env.getServers().remove(server);
						break;
					}
			// System.out.println("inainte de remove");
			// env.printFTPServers();

			// env.getFTPList().remove( env.getFTPServerById( ftpId));

			// System.out.println("dupa remove");
			// env.printFTPServers();
		}
	}

	public Server addServerForEnv(String envUID) {
		Server result = null;
		Environment env = environments.get(envUID);

		if (env != null) {
			result = new Server("Server nou", "44.44.44.44",
					"description noul server");
			env.addServer(result);
		}

		return result;
	}

	public MySQLService addMySQLServiceforServ(String srvUID) {
		MySQLService result = null;
		Server srv = null;
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) {
				for (Server server : serverList)
					if (server.getID() == srvUID) {
						srv = server;
						break;
					}
			}
		}

		if (srv != null) {
			result = new MySQLService();
			result.setName("New MySQLService");
			result.setDbName("test1");
			result.setPort("test2");
			result.setUser("test");
			result.setPassword("test2");
			srv.addService((Service) result);
		}

		return result;
	}

	public MongoService addMongoServiceforServ(String srvUID) {
		MongoService result = null;
		Server srv = null;
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) {
				for (Server server : serverList)
					if (server.getID() == srvUID) {
						srv = server;
						break;
					}
			}
		}

		if (srv != null) {
			result = new MongoService();
			result.setName("New MongoService");
			result.setDbName("test3");
			result.setPort("test3");
			result.setUser("test3");
			result.setPassword("test3");
			srv.addService((Service) result);
		}

		return result;
	}

	public void removeEnvironmentById(String uId) {
		environments.remove(uId);
	}

	public Server getServerByUID(String serverUID) {
		Server result = null;

		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) {
				for (Server server : serverList)
					if (server.getID() == serverUID) {
						result = server;
						break;
					}
			}
		}

		return result;
		// return new Server( "Server 3", "0909.05.05.05",
		// "description server 3");
	}

	public Service getServiceByUID(String serverUID, String serviceUID) {
		Service result = null;
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) {
				for (Server server : serverList)
					if (server.getID() == serverUID) {
						List<Service> serviceList = server.getServices();
						if (serviceList != null && !serviceList.isEmpty()) {
							for (Service service : serviceList) {
								if (service.getID() == serviceUID) {
									result = service;
									return result;
								}
							}
						}
					}
			}
		}

		return result;
	}

	public void removeService(String srvUID, String srcUID) {
		// Pentru implementarea cu Backendul, posibil ca functia sa necesite ID
		// enviormentului
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) {
				for (Server server : serverList)
					if (server.getID() == srvUID) {
						List<Service> serviceList = server.getServices();
						if (serviceList != null && !serviceList.isEmpty()) {
							for (Service service : serviceList) {
								if (service.getID() == srcUID) {
									server.getServices().remove(service);
									break;
								}
							}
						}
					}
			}
		}

	}
}