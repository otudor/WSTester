package com.wstester.variable;

import java.util.ArrayList;
import java.util.List;

import javassist.NotFoundException;

import org.w3c.dom.NodeList;

import com.wstester.exceptions.WsException;
import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IAssetManager;
import com.wstester.services.definition.IVariableManager;

public class DataProvider {

	CustomLogger log = new CustomLogger(DataProvider.class);
	IVariableManager variableManager;
	
	public List<Step> getDataProvider(Step step) throws Exception {
		
		variableManager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		
		log.info(step.getId(), "Data provider processor");
		
		String stepXML = VariableUtil.convertToXML(step);
		NodeList nodesWithVariables = VariableUtil.getNodesWithVariables(stepXML);
		List<Step> stepList = new ArrayList<Step>();
		
		for(int i=0; i<nodesWithVariables.getLength(); i++) {
			
			String nodeValue = nodesWithVariables.item(i).getFirstChild().getNodeValue();
			if(!nodeValue.trim().isEmpty()) {
				
				String variableName = nodeValue.substring(nodeValue.indexOf("${") + 2, nodeValue.indexOf("}"));
				Variable variable = variableManager.getVariableByName(variableName);
				
				if(variable != null && variable.getSelector() != null && variable.getSelector().startsWith("file:")) {
					List<String> dataProviderList = getDataProviderFromFile(variable, step.getId());
					
					for(String dataProviderValue : dataProviderList) {
						log.info(step.getId(), "Adding value " + dataProviderValue + " to the dataProvider");
						String newstepXML = stepXML.replace("${" + variableName + "}", dataProviderValue);
						Step stepWithoutVariables = VariableUtil.convertToStep(newstepXML);
						stepList.add(stepWithoutVariables);
					}
				}
				else if(variable == null) {
					log.error(step.getId(), "Variable with name: " + variableName + " was not found!");
					throw new NotFoundException("Variable with name: " + variableName + " was not found!");
				}
			}
		}
		// in case no dataProvider was specified just continue with the initial step
		if(stepList.isEmpty()) {
			stepList.add(step);
		}
		
		return stepList;
	}

	// selector in case of file is of type: file:asset.xml/row
	// if the row is missing by default take the first row
	private List<String> getDataProviderFromFile(Variable variable, String stepId) throws Exception {
		
		String selector = variable.getSelector();
		IAssetManager assetManager = ServiceLocator.getInstance().lookup(IAssetManager.class);
		try{
			
			String selectorPath = selector.substring(selector.indexOf(":") + 1);
			int columnToSelect = getColumnToSelect(selectorPath);
			String fileName = getFileName(selectorPath);
			
			log.info(stepId, "Building data provider from file: " + fileName + " using the column: " + columnToSelect);
			return assetManager.getCSVrow(fileName, columnToSelect);
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			throw new WsException(e, "The variable selector should be: response:selector or heder:selector");
		}
	}

	private int getColumnToSelect(String selectorPath) {
		
		int columnToSelect = 1;
		if(selectorPath.indexOf("/") != -1) {
			String row = selectorPath.substring(selectorPath.indexOf("/") + 1);
			columnToSelect = Integer.parseInt(row);
		}
		
		return columnToSelect;
	}
	

	private String getFileName(String selectorPath) {

		String fileName = "";
		if(selectorPath.indexOf("/") != -1) {
			fileName = selectorPath.substring(0, selectorPath.indexOf("/"));
		} else {
			fileName = selectorPath;
		}
		
		return fileName;
	}
}