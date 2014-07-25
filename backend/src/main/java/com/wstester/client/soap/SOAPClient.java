package com.wstester.client.soap;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;

public class SOAPClient {

	private String endpoint;
	
	public SOAPClient(String serverURL){
		
		this.endpoint = serverURL;
	}
	
	/**
	 * <p> Use this method when a SOAP request is made
	 * <p> In order to create a SOAPMessage, use the {@link com.wstester.client.soap.SOAPUtils#toSOAP() toSOAP()} method from the SOAPUtils class
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
	 * <p> In order to create a SOAPMessage, use the {@link com.wstester.client.soap.SOAPUtils#toSOAP() toSOAP()} method from the SOAPUtils class
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
