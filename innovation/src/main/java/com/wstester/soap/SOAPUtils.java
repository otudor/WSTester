package com.wstester.soap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

public class SOAPUtils {

	public static SOAPMessage toSOAP(String request) throws Exception {

		// SOAP Body
		InputStream is = new ByteArrayInputStream(request.getBytes());
		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, is);

		/* Print the request message */
		System.out.print("Request SOAP Message = ");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}

	public static SOAPMessage toSOAP(Object request) throws Exception {

		JAXBContext context = JAXBContext.newInstance(request.getClass());
		Marshaller m = context.createMarshaller();

		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Convert to SOAPMessage
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		m.marshal(request, os);
		String fixedRequest = fixSOAPRequestMessage(os.toString());
		InputStream is = new ByteArrayInputStream(fixedRequest.getBytes());

		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, is);

//		soapMessage.writeTo(System.out);
		return soapMessage;
	}

	public static String fromSOAP(SOAPMessage soapResponse) throws Exception {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		soapResponse.writeTo(out);
		return out.toString();
	}

	private static String fixSOAPRequestMessage(String messageFragment) throws IOException {
		String lvMessageFragment = messageFragment.substring(56);
		String top = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n"
				+ "  <soap12:Body>\n";
		String bottom = "  </soap12:Body>\n" + "</soap12:Envelope>";
		String wholeMessage = top + lvMessageFragment + bottom;
		return wholeMessage;
	}
}
