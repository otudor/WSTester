package com.wstester.testFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
				serverBox.setValue(server);
				
				// clear the service list and add the services from the current server
				serviceBox.getItems().clear();
				serviceBox.getItems().addAll(server.getServices());
				serviceBox.setValue(step.getService());
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
	
	private void setResponse(Step step) throws IOException {
		
		if(step.getLastExecution() != null) {
			Execution execution = step.getLastExecution();
			Response response = execution.getResponse();
			
			UtilityTool util = new UtilityTool();
			ResponseTabController responseTab = (ResponseTabController) FXMLLoader.load(getClass().getResource(util.getProperty(MainConstants.ResponseTabController)));
			
			if(response.getStatus() == ExecutionStatus.PASSED){
				responseTab.setResponseContent(response.getContent());
				System.out.println("am trecuttttttttttttttttttttttttttttttttttttttttttttttttttt");
				System.out.println("am trecuttttttttttttttttttttttttttttttttttttttttttttttttttt");
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
