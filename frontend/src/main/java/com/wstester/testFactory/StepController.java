package com.wstester.testFactory;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.wstester.model.Environment;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;

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
	
	public void setStep(String stepID) {
		this.stepId = stepID;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.serverChangedEvent();
	}

	public Server getServer() {
		return serverBox.getValue();
	}
	
	public Service getService() {
		return serviceBox.getValue();
	}
	
	public String getName(){
		return stepName.getText();
	}
	
	public void setCommonFields() {
		
		clearFields();
		
		populateFields();
	}

	private void clearFields() {
		
		serverBox.getItems().clear();
		serverBox.setValue(null);
		serviceBox.getItems().clear();
		serviceBox.setValue(null);
		stepName.setText("");
	}
	
	private void populateFields() {
		
		testProjectService = new TestProjectService();
		
    	Step step = testProjectService.getStep(stepId);
		Environment environment = testProjectService.getTestSuiteByStepUID(stepId).getEnvironment();
		
		if (environment != null) {
			// clear the server list and populate it with the servers from the current environment
			serverBox.getItems().addAll(environment.getServers());
			
			// set the current server
			Server server = step.getServer();
			if (server != null) {
				
				// clear the service list and add the services from the current server
				serviceBox.getItems().addAll(server.getServices());
				Service service = step.getService();
				if(service != null){
					serverBox.setValue(server);
					serviceBox.setValue(service);
					
				}
			}
		}
		
		// set the step name
		stepName.setText(step.getName());		
	}

	public void serverChangedEvent(){
		
		serverBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Server>() {
			public void changed(ObservableValue<? extends Server> ov, Server oldServer, Server newServer) {
				
				if(newServer != null && (oldServer==null || !oldServer.equals(newServer))) {
					//get the new services declared on the server
					serviceBox.setValue(null);
					serviceBox.getItems().clear();
					serviceBox.getItems().addAll(newServer.getServices());
				}
			}
		});
	}
}