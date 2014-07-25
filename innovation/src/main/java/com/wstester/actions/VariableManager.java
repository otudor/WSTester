package com.wstester.actions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.wstester.exceptions.WsErrorCode;
import com.wstester.exceptions.WsException;
import com.wstester.model.TestLevel;
import com.wstester.model.TestProject;
import com.wstester.model.Variable;

public class VariableManager {

	private HashMap<TestLevel, List<Variable>> map = new HashMap<TestLevel, List<Variable>>();
	private Long lastModified = null;
	
	public VariableManager(){
		
		String path = System.getProperty("Default Save Path");
		File testPlanFile = new File(path);
		lastModified = testPlanFile.lastModified();
	}
	
	public void setVariableContent(String uuid, Object content) throws WsException, IOException{
		
		Variable variable = searchVarialbeByUUID(uuid);
		
		if(content.getClass() != variable.getType().getClas()){
			// TODO: construct the error code of this exception, currently can't instantiate it
			WsErrorCode errorCode = null;
			throw new WsException("Variable " + variable.getName() + " is of type: " + variable.getType().getClas().getName() + " ;but the content is of type: " + content.getClass().getName(), errorCode);
		} else{
			
			variable.setContent(content);
		}
	}
	
	public Variable searchVarialbeByUUID(String uuid) throws WsException, IOException {

		updateMap();
		
		Set<TestLevel> keySet = map.keySet();
		
		for (TestLevel level : keySet) {
			List<Variable> variableList = map.get(level);
			for (Variable variable : variableList) {
				if (variable.getID().equals(uuid))
					return variable;
			}
		}
		
		// TODO: construct the error code of this exception, currently can't instantiate it
		WsErrorCode errorCode = null;
		throw new WsException("Variable was not found in the variable list", errorCode);
	}
	
	private void updateMap() throws IOException{
		
		TestProject project = null;
		
		String path = System.getProperty("Default Save Path");
		File testPlanFile = new File(path);
		long newLastModified = testPlanFile.lastModified();
		
		if(newLastModified > lastModified){
			TestProjectActions actions = new TestProjectActions();
			project = actions.open(path);
		}
		
		updateVariables(project);
	}

	private void updateVariables(TestProject project) {

//		project.getVariableList()
	}
}
