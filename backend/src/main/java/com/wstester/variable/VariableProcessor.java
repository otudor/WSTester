package com.wstester.variable;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import com.wstester.log.CustomLogger;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Header;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;
import com.wstester.services.definition.IVariableManager;

public class VariableProcessor implements Processor{

	CustomLogger log = new CustomLogger(VariableProcessor.class);
	IVariableManager variableManager;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		variableManager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		
		Response response = exchange.getIn().getBody(Response.class);
		if(response.getStatus() != ExecutionStatus.ERROR) {
			
			Step step = projectFinder.getStepById(response.getStepId());
			List<String> stepVariabelList = step.getVariableList();
			if(stepVariabelList != null) {
				
				for(String variableId : stepVariabelList) {
					Variable variable = variableManager.getVariable(variableId);
					
					if(variable.getSelector() != null) {
						
						String content = evaluateSelector(variable, response);
						variableManager.setVariableContent(variableId, content);
					}
				}
			}
		}
	}
	
	private String evaluateSelector(Variable variable, Response response) throws Exception {

		String selector = variable.getSelector();
		try{
			String type = selector.substring(0, selector.indexOf(":"));
			log.info(variable.getId(), "Selector is of type: " + type);
			
			String selectorPath = selector.substring(selector.indexOf(":") + 1);
			switch (type) {
				case "response" : return evaluateResponseSelector(variable.getId(), selectorPath, response);
				case "header" : return evaluateHeaderSelector(variable.getId(), selectorPath, response);
				default : return "The type of the variable was not set correctly. Variable selector should be: response:selector or heder:selector";
			}
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			return "The variable selector should be: response:selector or heder:selector";
		}
	}

	private String evaluateResponseSelector(String variableId, String selector, Response response) throws Exception {
		
		log.info(variableId, "Applying the selector: " + selector);
		try {
			String selected = getContentFromXML(response.getContent(), variableId, selector);
			log.info(variableId, "Variable was set from XML to: " + selected);
			return selected;
		} catch(SAXException xmlException) {
			
			try {
				String selected = getContentFromJson(response.getContent(), variableId, selector);
				log.info(variableId, "Variable was set from Json to: " + selected);
				return selected;
			} catch (InvalidJsonException|IllegalArgumentException e) {
			
				e.printStackTrace();
				log.info(variableId, "Variable was set from String to: " + response.getContent());
				return response.getContent();
			}
		}
	}

	private String evaluateHeaderSelector(String string, String selector, Response response) {
		
		List<Header> headerList = response.getHeaderList();
		try {
			Header foundHeader = headerList.stream().filter(header -> header.getKeyField().equals(selector)).findFirst().get();
			return foundHeader.getValueField();
		} catch (NoSuchElementException e) {
			
			e.printStackTrace();
			return "Header " + selector + " not found in the headerList";
		} catch (NullPointerException e) {
			
			e.printStackTrace();
			return "Header List of the response is null";
		}
	}
	
	private String getContentFromXML(String content, String variableId, String selector) throws Exception {
		
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource(new ByteArrayInputStream(content.getBytes("utf-8")));

		Document xmlDocument = db.parse(is);
		XPath xPath = XPathFactory.newInstance().newXPath();

		Node value = (Node)xPath.compile(selector).evaluate(xmlDocument, XPathConstants.NODE);
		return value.getFirstChild().getNodeValue();
	}
	
	private String getContentFromJson(String content, String variableId, String selector) {

		content = content.replaceAll("=", ":");
		String selectedValue = JsonPath.read(content, selector);
		return selectedValue;
	}
}