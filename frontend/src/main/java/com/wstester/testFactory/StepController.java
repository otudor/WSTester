package com.wstester.testFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import com.wstester.elements.Dialog;
import com.wstester.main.MainLauncher;
import com.wstester.model.Environment;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

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
	private ComboBox<Step> dependsOn;
	
	private String stepId;
	
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
	
	public String getName() {
		return stepName.getText();
	}
	
	public String getDependsOn() {
		return (String) dependsOn.getValue().getId();
	}
	
	public void setCommonFields(List<String> higherTestList) {
		
		clearFields();
		
		populateFields(higherTestList);
	}

	private void clearFields() {
		
		serverBox.getItems().clear();
		serverBox.setValue(null);
		serviceBox.getItems().clear();
		serviceBox.setValue(null);
		stepName.setText("");
		dependsOn.getItems().clear();
		dependsOn.setValue(null);
	}
	
	private void populateFields(List<String> higherTestList) {
		
		IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			Dialog.errorDialog("Could not get the TestSuite environement!", MainLauncher.stage);
		}
		
		Step step = projectFinder.getStepById(stepId);
		String environmentId = projectFinder.getTestSuiteByStepId(stepId).getEnvironmentId();
		Environment environment = projectFinder.getEnvironmentById(environmentId);
		
		if (environment != null) {
			// clear the server list and populate it with the servers from the current environment
			serverBox.getItems().addAll(environment.getServers());
			
			// set the current server
			Server server = projectFinder.getServerById(step.getServerId());
			if (server != null) {
				
				// clear the service list and add the services from the current server
				serviceBox.getItems().addAll(server.getServices());
				Service service = projectFinder.getServiceById(step.getServiceId());
				if(service != null){
					serverBox.setValue(server);
					serviceBox.setValue(service);
					
				}
			}
		}
		
		// set the step name
		stepName.setText(step.getName());	
		
		// set the dependsOn
		for(String stepId : higherTestList) {
			
			Step higherStep= projectFinder.getStepById(stepId);
			dependsOn.getItems().add(higherStep);
			
			if(step.getDependsOn() != null && step.getDependsOn().equals(stepId)) {
				dependsOn.setValue(higherStep);
			}
		}
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