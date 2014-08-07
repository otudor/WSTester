package com.wstester;

import com.wstester.model.Server;

public class ServerService
{
    public ServerService()
    {
    	
    }

    public Server getServerByUID( String serverUID)
    {
    	return new Server( "Server 3", "0909.05.05.05", "description server 3");
    }
    
    
}