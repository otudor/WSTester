package com.wstester.variable;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.fusesource.hawtbuf.ByteArrayInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.wstester.log.CustomLogger;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;
import com.wstester.services.definition.IVariableManager;

public class VariableProcessor implements Processor{

	CustomLogger log = new CustomLogger(VariableProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		IVariableManager variableManager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		
		Response response = exchange.getIn().getBody(Response.class);
		if(response.getStatus() != ExecutionStatus.ERROR) {
			
			Step step = projectFinder.getStepById(response.getStepId());
			List<String> stepVariabelList = step.getVariableList();
			if(stepVariabelList != null) {
				
				for(String variableId : stepVariabelList) {
					Variable variable = variableManager.getVariable(variableId);
					
					if(variable.getSelector() != null) {
						
						String content = evaluateSelector(variable, response);
						try {
							String selected = getContentFromXML(response.getContent(), variable);
							variableManager.setVariableContent(variableId, selected);
							log.info(variable.getId(), "Variable was set from XML to: " + selected);
						} catch(SAXException xmlException) {
							
							log.info(variable.getId(), "Variable was set from String to: " + response.getContent());
							variableManager.setVariableContent(variableId, response.getContent());
						}
					}
				}
			}
		}
	}
	
	private String evaluateSelector(Variable variable, Response response) {

		String selector = variable.getSelector();
		String type = selector.substring(0, selector.indexOf(":"));
		switch (type) {
			case "response" : evaluateResponseSelector();
			case "header" : evaluateHeaderSelector();
		}
			
		return null;
	}

	private String getContentFromXML(String content, Variable variable) throws Exception {
		
		log.info(variable.getId(), "Applying the selector: " + variable.getSelector());
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource(new ByteArrayInputStream(content.getBytes("utf-8")));

		Document xmlDocument = db.parse(is);
		XPath xPath = XPathFactory.newInstance().newXPath();

		Node value = (Node)xPath.compile(variable.getSelector()).evaluate(xmlDocument, XPathConstants.NODE);
		return value.getFirstChild().getNodeValue();
	}
}
