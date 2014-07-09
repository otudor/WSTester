package com.wstester.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

public class SOAPUtils {

	public static SOAPMessage createSOAPRequest(String request) throws Exception {

        // SOAP Body
        InputStream is = new ByteArrayInputStream(request.getBytes());
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, is);

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
	
    public static String createSOAPResponse(SOAPMessage soapResponse) throws Exception {
    	
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapResponse.writeTo(out);
        return out.toString();
    }
}
