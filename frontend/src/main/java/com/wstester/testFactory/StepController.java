package com.wstester.testFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.codehaus.jettison.json.JSONException;
import org.xml.sax.SAXException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import com.wstester.assets.EditController;
import com.wstester.model.Environment;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.util.MainConstants;
import com.wstester.util.UtilityTool;

/**
 * Template class which supports fx inclusion in different views
 * <br>
 * Do not modify it without consider affecting sensitive views of the application
 * 
 * @author lvasile
 * @since 2014/10/10
 */
public class StepController implements Initializable{
	
	@FXML
	private ComboBox<Service> serviceBox;
	@FXML
	private ComboBox<Server> serverBox;
	@FXML
	private TextField stepName;
	@FXML
	private Button saveBtn; 
	@FXML 
	private Pane treePane;
	
	private String stepId;
	private TestProjectService testProjectService;
	
	public void setStep(String stepID){
		this.stepId = stepID;
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.serverChangedEvent();
		this.serviceChangedEvent();
		
		
		
	}
	
	



	public Server getServer(){
		return serverBox.getValue();
	}
	
	public Service getService(){
		return serviceBox.getValue();
	}
	
	public void setCommonFields() {
		testProjectService = new TestProjectService();
		
    	Step step = testProjectService.getStep(stepId);
		Environment environment = testProjectService.getTestSuiteByStepUID(stepId).getEnvironment();
	
		if (environment != null) {
			// clear the server list and populate it with the servers from the current environment
			serverBox.getItems().clear();
			serverBox.getItems().addAll(environment.getServers());
			
			// set the current server
			Server server = step.getServer();
			if (server != null) {
//				serverBox.setValue(server);
				
				// clear the service list and add the services from the current server
				serviceBox.getItems().clear();
				serviceBox.getItems().addAll(server.getServices());
			//	serviceBox.getItems().addAll(step.getService());
				Service service = step.getService();
				//if(service != null){
					serverBox.setValue(server);
					serviceBox.setValue(service);
					
				//}
			}
		}
		
		// set the step name
		stepName.setText(step.getName());
		
		try{
			setResponse(step);
		} catch (IOException e){
			
			e.printStackTrace();
		}
	}
	
	public void setResponseContent(String response) {
    	
    	
		try {
			XmlParser xmlParser = new XmlParser();
			
			treePane.getChildren().clear();
			treePane.getChildren().add(xmlParser.getTreeViewOfXml(response));
		} catch (SAXException xmlException) {
			
//			try{
			// TODO: parse the JSON
			//treePane.getChildren().add(jsonParser
//			} catch (JSONException jsonException) {
				Label label = new Label(response);
				treePane.getChildren().add(label);
//			}
		} catch (Exception e){
			// TODO: auto-generated catch 
			e.printStackTrace();
		}
	}
	
	 private void setResponse(Step step) throws IOException {
	    	
			if(step.getLastExecution() != null) {
				Execution execution = step.getLastExecution();
				Response response = execution.getResponse();
				
				
				
				if(response.getStatus() == ExecutionStatus.PASSED){
				setResponseContent(response.getContent());
				}
				}
	  
	    }
	


	public void serverChangedEvent(){
		
		serverBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Server>() {
			public void changed(ObservableValue<? extends Server> ov, Server oldServer, Server newServer) {
				
				Step step = testProjectService.getStep(stepId);
				
				if(newServer != null && (oldServer==null || !oldServer.equals(newServer))) {
					step.setServer(newServer);
					step.setService(null);
					
					//get the new services declared on the server
					serviceBox.getItems().clear();
					serviceBox.getItems().addAll(newServer.getServices());
				}
			}
		});
	}
	
	public void serviceChangedEvent(){
		
		serviceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Service>() {
			public void changed(ObservableValue<? extends Service> ov, Service oldService, Service newService) {
				
				Step step = testProjectService.getStep(stepId);
				
				if(newService !=null && (oldService==null || !oldService.equals(newService))){
					step.setService(newService);
				}
			}
		});
	}
}
