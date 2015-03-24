package com.wstester.variable;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.fusesource.hawtbuf.ByteArrayInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.wstester.model.Step;

public class VariableUtil {

	public static NodeList getNodesWithVariables(String stepXML) throws Exception{
		
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource(new ByteArrayInputStream(stepXML.getBytes()));

		Document xmlDocument = db.parse(is);
		XPath xPath = XPathFactory.newInstance().newXPath();

		return (NodeList)xPath.compile("//*[name() != 'assertList']/*[contains(.,'${')]").evaluate(xmlDocument, XPathConstants.NODESET);
	}

	public static String convertToXML(Step step) throws JAXBException {
		
		JAXBContext context = JAXBContext.newInstance(Step.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter stringWriter = new StringWriter();
		m.marshal(step, stringWriter);
		
		return stringWriter.getBuffer().toString();
	}
	
	public static Step convertToStep(String stepXML) throws Exception{
		
		JAXBContext jc = JAXBContext.newInstance(Step.class);
		Unmarshaller u = jc.createUnmarshaller();
		InputSource is = new InputSource(new ByteArrayInputStream(stepXML.getBytes()));
		Step step = (Step) u.unmarshal(is);
		
		return step;
	}
}