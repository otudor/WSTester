package com.wstester;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
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
	
	/**
	 * Use this method when a REST request is made
	 * @param request - any HHTP request from the API
	 * @return the {@link com.sun.jersey.api.client.ClientResponse response} from the server 
	 */
	public ClientResponse sendRestRequest(Request request){
		
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
	 * <p> Use this method when a SOAP request is made
	 * <p> In order to create a SOAPMessage, use the {@link com.wstester.util.SOAPUtils#toSOAP() toSOAP()} method from the SOAPUtils class
	 * @param Object representation of the SOAP request
	 * @return String representation of the SOAP response
	 * @throws Exception 
	 */
	public String sendSOAPRequest(Object soapRequest) throws Exception{
		
		SOAPMessage req = SOAPUtils.toSOAP(soapRequest);
        
        return callSOAPServer(req);
	}
	
	/**
	 * <p> Use this method when a SOAP request is made
	 * <p> In order to create a SOAPMessage, use the {@link com.wstester.util.SOAPUtils#toSOAP() toSOAP()} method from the SOAPUtils class
	 * @param String representation of the SOAP request
	 * @return String representation of the SOAP response
	 * @throws Exception 
	 */
	public String sendSOAPRequest(String soapRequest) throws Exception{
		
		SOAPMessage req = SOAPUtils.toSOAP(soapRequest);
        
        return callSOAPServer(req);
	}
	
	private String callSOAPServer(SOAPMessage req) throws Exception{
		
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        SOAPMessage soapResponse = soapConnection.call(req, endpoint);

        soapConnection.close();
        
        String stringResponse = SOAPUtils.fromSOAP(soapResponse);
        
        return stringResponse;
	}
}
