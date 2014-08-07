package com.wstester;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wstester.model.Environment;
import com.wstester.model.Server;

public class EnvironmentService
{
    //private long nextId = 0;
    private Map<String, Environment> environments;

    public EnvironmentService()
    {
        this.environments = new HashMap<String, Environment>();
        //createContact("Cathy", "Freeman");
        Environment e1 = createEnvironment("CT");
        List<Server> list1 = new ArrayList<Server>();
        list1.add( new Server("FTP1", "44.44.44.44", "description ftp server"));
        e1.setServers( list1);
        
        Environment e2 = createEnvironment("CHINA Env");
        List<Server> list2 = new ArrayList<Server>();
        list2.add( new Server("FTP2", "55.55.55.55", "description China server"));
        e2.setServers( list2);
        
        Environment e3 = createEnvironment("UK Env");
        List<Server> list3 = new ArrayList<Server>();
        list3.add( new Server("FTP3", "55.99.80.71", "description UK server"));
        e3.setServers( list3);
    }

    public Environment createEnvironment(String name)
    {
    	Environment env = new Environment( name);
        environments.put( env.getID(), env);
        return env;
    }

    public List<Environment> loadEnvironments()
    {
        List<Environment> matches = new ArrayList<Environment>();
        matches.addAll( environments.values());

        return matches;
    }

    public Environment getEnvironment( String uID)
    {
        return environments.get( uID);
    }
    
    public Environment getFirstEnv()
    {
    	Environment result = null;
    	if ( !environments.isEmpty())
    		result = environments.get( environments.keySet().toArray()[0]);
        
    	return result;
    }
    
    public void removeServer( String uID, String ftpId)
    {
    	Environment env = environments.get( uID);
    	
    	if( env != null)
    	{
    		//System.out.println("inainte de remove");
    		//env.printFTPServers();
    		
    		//env.getFTPList().remove( env.getFTPServerById( ftpId));
    		
    		//System.out.println("dupa remove");
    		//env.printFTPServers();
    	}
    }
    
    public void removeEnvironmentById( String uId)
    {
    	environments.remove( uId);
    }

}