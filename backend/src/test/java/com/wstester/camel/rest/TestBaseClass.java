package com.wstester.camel.rest;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import com.wstester.server.Main;

public class TestBaseClass extends com.wstester.camel.TestBaseClass {

	private static HttpServer server;
	
    @BeforeClass
    public static void setUp() throws Exception {
        server = Main.startRestServer();
    }
 
    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }
}
