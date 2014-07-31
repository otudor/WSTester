package com.wstester.client.soap;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import com.wstester.client.Client;


public class SOAPClient extends Client{

	private String endpoint;
	private SOAPConnection soapConnection;
	
	public SOAPClient(String serverURL) throws UnsupportedOperationException, SOAPException{
		
		this.endpoint = serverURL;
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        soapConnection = soapConnectionFactory.createConnection();
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
		
        // Send SOAP Message to SOAP Server
        SOAPMessage soapResponse = soapConnection.call(req, endpoint);

        String stringResponse = SOAPUtils.fromSOAP(soapResponse);
        
        return stringResponse;
	}

	@Override
	public void close() throws SOAPException {
		
		soapConnection.close();
	}
}
