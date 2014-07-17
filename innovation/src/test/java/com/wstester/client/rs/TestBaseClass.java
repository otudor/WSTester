package com.wstester.client.rs;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.wstester.client.rest.RestClient;
import com.wstester.server.Main;

public class TestBaseClass {

	private static HttpServer server;
	protected static RestClient client;
	
    @BeforeClass
    public static void setUp() throws Exception {
        server = Main.startRestServer();
        client = new RestClient("http://localhost:9997");
    }
 
    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
        client.close();
    }
}
