package com.wstester.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TreeItem;

import com.sun.xml.bind.v2.util.QNameMap.Entry;
import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLService;
import com.wstester.model.RestService;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapService;
import com.wstester.model.TestProject;
import com.wstester.util.*;

public class EnvironmentService {
	// private long nextId = 0;
	private Map<String, Environment> environments;
	private TestProject testProject;
	

	public EnvironmentService() {
		this.environments = new HashMap<String, Environment>();
		// createContact("Cathy", "Freeman");
		testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		System.out.println(testProject);
		if(testProject.getEnvironmentList()!=null)
		{
		for(Environment entry:testProject.getEnvironmentList())
		{
			environments.put(entry.getID(), entry);
		}
		}
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

	public SoapService addSoapServiceforServ(String srvUID) {
		SoapService result = null;
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
			result = new SoapService();
			result.setName("New SoapService");
			result.setWsdlURL("test3");
			result.setPort("test3");
			result.setPath("test3");
			srv.addService((Service) result);
		}

		return result;
	}

	public RestService addRestServiceforServ(String srvUID) {
		RestService result = null;
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
			result = new RestService();
			result.setName("New RestService");
			result.setPort("00000");
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

	public void setMongoServiceByUID(MongoService src, String serviceUID) {
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) 
			{
				for (Server server : serverList)
				{
					List<Service> serviceList = server.getServices();
						if (serviceList != null && !serviceList.isEmpty())
						{
							for (Service service : serviceList) 
							{
								if (service.getID() == serviceUID) 
								{
											MongoService mongo = new MongoService();
											mongo = (MongoService) service;
											mongo.setDbName(src.getDbName());
											mongo.setPassword(src.getPassword());
											mongo.setPort(src.getPort());
											mongo.setUser(src.getUser());
											service = (Service) mongo;
								}			
							}
						}
				}
			}
		}
	}

	public void setMySQLServiceByUID(MySQLService src, String serviceUID) {
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) 
			{
				for (Server server : serverList)
				{
					List<Service> serviceList = server.getServices();
						if (serviceList != null && !serviceList.isEmpty())
						{
							for (Service service : serviceList) 
							{
								if (service.getID() == serviceUID) 
								{
											MySQLService serv = new MySQLService();
											serv = (MySQLService) service;
											serv.setDbName(src.getDbName());
											serv.setPassword(src.getPassword());
											serv.setPort(src.getPort());
											serv.setUser(src.getUser());
											service = (Service) serv;
								}			
							}
						}
				}
			}
		}
	}
	
	public void setRestServiceByUID(RestService src, String serviceUID) {
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) 
			{
				for (Server server : serverList)
				{
					List<Service> serviceList = server.getServices();
						if (serviceList != null && !serviceList.isEmpty())
						{
							for (Service service : serviceList) 
							{
								if (service.getID() == serviceUID) 
								{
											RestService serv = new RestService();
											serv = (RestService) service;
											serv.setPort(src.getPort());
											service = (Service) serv;
								}			
							}
						}
				}
			}
		}
	}
	
	public void setSoapServiceByUID(SoapService src, String serviceUID) {
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) 
			{
				for (Server server : serverList)
				{
					List<Service> serviceList = server.getServices();
						if (serviceList != null && !serviceList.isEmpty())
						{
							for (Service service : serviceList) 
							{
								if (service.getID() == serviceUID) 
								{
											SoapService serv = new SoapService();
											serv = (SoapService) service;
											serv.setPath(src.getPath());
											serv.setWsdlURL(src.getWsdlURL());
											serv.setPort(src.getPort());
											service = (Service) serv;
								}			
							}
						}
				}
			}
		}
	}
	
	public void setServerByUID(Server srv,String serverUID) {
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<Server> serverList = ((Environment) entry.getValue())
					.getServers();
			if (serverList != null && !serverList.isEmpty()) 
			{
				for (Server server : serverList)
				{
					if (server.getID() == serverUID) 
						{
							server.setName(srv.getName());
							server.setIp(srv.getIp());
							server.setDescription(srv.getDescription());
						}			
				}
			}
		}
	}
	
	public void setEnvNameByUID(String name,String envUID) {
		for (Map.Entry<String, Environment> entry : environments.entrySet()) {
			if (entry.getValue().getID() == envUID) 
						{
							entry.getValue().setName(name);
							
						}			
		}
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
	public void saveEnv()
	{	
		List<Environment> list = new ArrayList<>();
		for(Environment entry : environments.values() )
		{
			list.add(entry);
		}
		((TestProject)UtilityTool.getEntity(MainConstants.TESTPROJECT)).setEnvironmentList(list);
	}
}