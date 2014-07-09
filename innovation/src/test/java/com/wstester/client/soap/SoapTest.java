package com.wstester.client.soap;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import com.wstester.Client;

public class SoapTest {

	@Test
	public void test() throws Exception {

		 Client client = new Client("http://footballpool.dataaccess.eu/data/info.wso");

		String content = new String(Files.readAllBytes(Paths.get("src/test/resources/SOAPRequest.xml")));
		
		String soapResponse = client.sendRequest(content);
		System.out.println("\nResponse SOAP Message:");
		System.out.println(soapResponse);
	}
}
