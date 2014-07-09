package com.wstester;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.wstester.api.GetRequest;
import com.wstester.api.PostRequest;
import com.wstester.api.Request;
import com.wstester.util.SOAPUtils;

public class Client {

	private ClientResponse response;
	private com.sun.jersey.api.client.Client client;
	private WebResource resource;
	private String endpoint;
	
	public Client(String serverURL){
		
		this.endpoint = serverURL;
	}
	
	//TODO construct the login method
	public void login(String username, String password){
		
	}
	
	public ClientResponse sendRequest(Request request){
		
		client = com.sun.jersey.api.client.Client.create();
		resource = client.resource(endpoint);
		request.setClient(resource);
		
		if(request instanceof PostRequest || request instanceof GetRequest){
			response = request.sendRequest();
		}
		else{
			throw new IllegalArgumentException("Request type " + request.getClass().getName() + " unkown!");
		}
		
		return response;
	}
	
	/**
	 * Use this method when a SOAP request is made
	 * @param String representation of the SOAP request
	 * @return String representation of the SOAP response
	 * @throws Exception 
	 */
	public String sendRequest(String request) throws Exception{
		
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        SOAPMessage soapResponse = soapConnection.call(SOAPUtils.createSOAPRequest(request), endpoint);

        soapConnection.close();
        
        String stringResponse = SOAPUtils.createSOAPResponse(soapResponse);
        return stringResponse;
	}
}
