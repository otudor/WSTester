package com.wstester.client.soap;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import com.wstester.soap.SOAPClient;
import eu.dataaccess.footballpool.AllForwards;

public class SoapTest {

	@Test
	public void getRequestFromFile() throws Exception {

		SOAPClient client = new SOAPClient("http://footballpool.dataaccess.eu/data/info.wso");

		String content = new String(Files.readAllBytes(Paths.get("src/test/resources/SOAPRequest.xml")));
		
		String soapResponse = client.sendSOAPRequest(content);
		System.out.println("\nResponse SOAP Message:");
		System.out.println(soapResponse);
	}
	
	@Test
	public void getRequestFromObject() throws Exception{
		
		SOAPClient client = new SOAPClient("http://footballpool.dataaccess.eu/data/info.wso");
		
		AllForwards fromBrazil = new AllForwards();
		fromBrazil.setSCountryName("Brazil");
		
		String soapResponse = client.sendSOAPRequest(fromBrazil);
		System.out.println("\nResponse SOAP Message:");
		System.out.println(soapResponse);
	}
}
