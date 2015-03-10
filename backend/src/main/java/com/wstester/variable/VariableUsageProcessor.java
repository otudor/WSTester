package com.wstester.variable;

import java.io.StringWriter;

import javassist.NotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.fusesource.hawtbuf.ByteArrayInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.wstester.dispatcher.ExchangeDelayer;
import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IVariableManager;

public class VariableUsageProcessor implements Processor {

	CustomLogger log = new CustomLogger(VariableUsageProcessor.class);
	IVariableManager variableManager;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		variableManager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		
		Step step = exchange.getIn().getBody(Step.class);
		log.info(step.getId(), "Replacing variables");
		
		String stepXML = convertToXML(step);
		NodeList nodesWithVariables = getNodesWithVariables(stepXML);
		
		for(int i=0; i<nodesWithVariables.getLength(); i++) {
			
			String nodeValue = nodesWithVariables.item(i).getFirstChild().getNodeValue();
			if(!nodeValue.trim().isEmpty()) {
				
				String variableName = nodeValue.substring(nodeValue.indexOf("${") + 2, nodeValue.indexOf("}"));
				Variable variable = variableManager.getVariableByName(variableName);
				
				if(variable != null) {
					if(variable.getContent() == null || variable.getContent().trim().isEmpty()) {
						
						log.info(step.getId(), "Waiting for variable: " + variable.getId());
						boolean succeeded = new ExchangeDelayer().delayByVariable(variable.getId());
						
						if(!succeeded) {
							log.error(step.getId(), "Variable with name: " + variableName + " has empty content!");
							throw new NotFoundException("Variable with name: " + variableName + " has empty content!");
						}
					}
					
					log.info(step.getId(), "Replacing variable " + variableName + " with " + variable.getContent());
					stepXML = stepXML.replace("${" + variableName + "}", variable.getContent());
					
				} else {
					log.error(step.getId(), "Variable with name: " + variableName + " was not found!");
					throw new NotFoundException("Variable with name: " + variableName + " was not found!");
				}
				
			}
		}
		
		Step stepWithoutVariables = convertToStep(stepXML);
		exchange.getIn().setBody(stepWithoutVariables);
	}

	private NodeList getNodesWithVariables(String stepXML) throws Exception{
		
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource(new ByteArrayInputStream(stepXML.getBytes()));

		Document xmlDocument = db.parse(is);
		XPath xPath = XPathFactory.newInstance().newXPath();

		return (NodeList)xPath.compile("//*[contains(.,'${')]").evaluate(xmlDocument, XPathConstants.NODESET);
	}

	private String convertToXML(Step step) throws JAXBException {
		
		JAXBContext context = JAXBContext.newInstance(Step.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter stringWriter = new StringWriter();
		m.marshal(step, stringWriter);
		
		return stringWriter.getBuffer().toString();
	}
	
	private Step convertToStep(String stepXML) throws Exception{
		
		JAXBContext jc = JAXBContext.newInstance(Step.class);
		Unmarshaller u = jc.createUnmarshaller();
		InputSource is = new InputSource(new ByteArrayInputStream(stepXML.getBytes()));
		Step step = (Step) u.unmarshal(is);
		
		return step;
	}
}
