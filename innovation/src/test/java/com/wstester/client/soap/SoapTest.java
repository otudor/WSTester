package com.wstester.client.soap;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import com.wstester.client.soap.SOAPClient;

import eu.dataaccess.footballpool.AllForwards;

public class SoapTest {

	private static SOAPClient client;
	
	@BeforeClass
	public static void setUp(){
		
		client = new SOAPClient("http://footballpool.dataaccess.eu/data/info.wso");
	}
	
	@Test
	public void getRequestFromFile() throws Exception {

		String content = new String(Files.readAllBytes(Paths.get("src/test/resources/SOAPRequest.xml")));
		
		String soapResponse = client.sendSOAPRequest(content);
		System.out.println("\nResponse SOAP Message:");
		System.out.println(soapResponse);
	}
	
	@Test
	public void getRequestFromObject() throws Exception{
		
		
		AllForwards fromBrazil = new AllForwards();
		fromBrazil.setSCountryName("Brazil");
		
		String soapResponse = client.sendSOAPRequest(fromBrazil);
		System.out.println("\nResponse SOAP Message:");
		System.out.println(soapResponse);
	}
}
