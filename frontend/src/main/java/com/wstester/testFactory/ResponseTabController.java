package com.wstester.testFactory;

import java.io.IOException;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;

import com.wstester.elements.Dialog;
import com.wstester.main.MainLauncher;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ITestRunner;
import com.wstester.util.MainConstants;

public class ResponseTabController {

	@FXML
	private TabPane responseListTabPane;

	public void setResponse(Step step) {

		// get the response for the responses for the step
		ITestRunner testRunner = null;
		try {
			testRunner = ServiceLocator.getInstance().lookup(ITestRunner.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("The test couldn't be run. Please try again!", MainLauncher.stage);
		}
		List<Response> responseList = testRunner.getResponseList(step.getId(), 1000L);
		
		responseListTabPane.getTabs().clear();
		if (responseList != null) {
			for (Response response : responseList) {
				addResponseTab(response);
			}
		}
	}

	private void addResponseTab(Response response) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.RESPONSE.toString()));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			Dialog.errorDialog("Can't open the test factory window. Please try again!", MainLauncher.stage);
		}
		ResponseController responseController = loader.<ResponseController> getController();
		responseController.setRespose(response);
		
		Tab newTab = getTab(response);
		newTab.setContent(loader.getRoot());
		newTab.setOnSelectionChanged(new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				responseController.initializeGoToAssertButton(response);
			}
		});
		responseListTabPane.getTabs().add(newTab);
	}
	
	private Tab getTab(Response response) {
		
		Tab newTab = new Tab();
		Label responseLabel = new Label("Response");
		
		if (response.getStatus() == ExecutionStatus.PASSED) {
			responseLabel.setStyle("-fx-text-fill: green");
		} else if (response.getStatus() == ExecutionStatus.ERROR) {
			responseLabel.setStyle("-fx-text-fill: firebrick");
		} else if (response.getStatus() == ExecutionStatus.FAILED) {
			responseLabel.setStyle("-fx-text-fill: blue");
		}
		
		StackPane stackPane = new StackPane(new Group(responseLabel));
		newTab.setGraphic(stackPane);
		
		return newTab;
	}
}