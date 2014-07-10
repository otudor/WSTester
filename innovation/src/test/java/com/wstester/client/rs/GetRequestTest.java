package com.wstester.client.rs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.wstester.rest.GetRequest;
import com.wstester.rest.Request;
import com.wstester.rest.RestClient;
import com.wstester.server.Main;

public class GetRequestTest {

	private static HttpServer server;
	private static RestClient client;
 
    @BeforeClass
    public static void setUp() throws Exception {
        server = Main.startRestServer();
        client = new RestClient("http://localhost:9998");
    }
 
    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }
 

    @Test
    public void multiplePath() {
        
    	Request request = new GetRequest();
    	request.setPath("customer","getCustomers");
    	
		ClientResponse response = client.sendRestRequest(request);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals("All customers", response.getEntity(String.class));
    }
    
    @Test
    public void pathAndParams(){
    	
    	Request request = new GetRequest();
    	request.setPath("customer", "searchCustomer");
    	MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    	String name = "Sayan";
    	params.add("name", name);
		request.setParams(params);
		
		ClientResponse response = client.sendRestRequest(request);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(name, response.getEntity(String.class));
    }
    
    @Test
    public void pathAndCookie(){
    	
    	Request request = new GetRequest();
    	request.setPath("customer", "searchWithCookie");
    	String name = "Super Sayan";
    	ArrayList<Cookie> cookies = new ArrayList<Cookie>();
    	Cookie cookie = new Cookie("name", name);
    	cookies.add(cookie);
		request.setCookies(cookies);
		
		ClientResponse response = client.sendRestRequest(request);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(name, response.getEntity(String.class));
    }
    
    @Test
    public void pathAndHeader(){
    	
    	Request request = new GetRequest();
    	request.setPath("customer", "searchWithHeader");
    	MultivaluedMap<String, String> header = new MultivaluedMapImpl();
    	String name = "Super Sayan 2";
    	String anotherName = "Super Sayan 3";
    	header.add("name", name);
    	header.add("name", anotherName);
		request.setHeader(header);
		
		ClientResponse response = client.sendRestRequest(request);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(name + "," + anotherName, response.getEntity(String.class));
    }
    
    @Test
    public void pathParamHeaderCookie(){
    	
    	Request request = new GetRequest();
    	request.setPath("customer","searchWithAll");
    	
    	MultivaluedMap<String, String> params = new MultivaluedMapImpl();
    	String paramName = "Sayan";
    	params.add("queryName", paramName);
		request.setParams(params);
		
    	String cookieName = "Super Sayan";
    	ArrayList<Cookie> cookies = new ArrayList<Cookie>();
    	Cookie cookie = new Cookie("cookieName", cookieName);
    	cookies.add(cookie);
		request.setCookies(cookies);
		
		MultivaluedMap<String, String> header = new MultivaluedMapImpl();
    	String headerName = "Super Sayan 2";
    	header.add("headerName", headerName);
    	request.setHeader(header);
    	
    	ClientResponse response = client.sendRestRequest(request);
    	assertEquals(Status.OK.getStatusCode(), response.getStatus());
    	assertEquals(paramName + "," + cookieName + "," + headerName, response.getEntity(String.class));
    }
}
