package com.wstester.testFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.RestStep;
import com.wstester.model.Step;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ResponseTabController implements Initializable {
	
	@FXML 
	private AnchorPane rootPane;
	@FXML 
	private Pane treePane;
	
	private TestProjectService testProjectService;
	private String stepId;
	
    public AnchorPane getView() {
        return rootPane;
    }
   
    
   
    	   
    public Pane setResponseContent(String response) {
    	
    	
		try {
			XmlParser xmlParser = new XmlParser();
			
			treePane.getChildren().clear();
			treePane.getChildren().add(xmlParser.getTreeViewOfXml(response));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return treePane;
	}
    
//    private void setResponse() throws IOException {
//    	Step step = testProjectService.getStep(stepId);
//		if(step.getLastExecution() != null) {
//			Execution execution = step.getLastExecution();
//			Response response = execution.getResponse();
//			
//			
//			
//			if(response.getStatus() == ExecutionStatus.PASSED){
//			setResponseContent("<data><employee><name>A</name></employee></data>");
//			}
//			}
//    }
//    public Pane getTree(){
//    	return treePane;
//    }




	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		try {
//			this.setResponse();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}



	
}
