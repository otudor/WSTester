package com.wstester.environment;


import com.wstester.model.RestService;
import com.wstester.model.Server;
import com.wstester.model.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RestPresenter {
	@FXML
	private Node restPanel;
	@FXML
	private TextField restField;
	@FXML
	private Button saveRest;
	@FXML
	private Button editRest;

	public void saveRest(ActionEvent e) {
		saveRest.setDisable(true);
		restField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		restField.setEditable(false);


	}
	
	public void editRest(ActionEvent e) {
		saveRest.setDisable(false);
		
		restField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
		restField.setEditable(true);

	}

	private EnvironmentService envService;
	private MainPresenter mainPresenter;

	public void setEnvironmentService(EnvironmentService envService) {
		this.envService = envService;
	}

	public void setMainPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	public Node getView() {
		return restPanel;
	}

	public void setRest( final String serverUID, final String serviceUID) {
		saveRest.setDisable(true);
		saveRest.setStyle("-fx-base: #b6e7c9;");
		restField.setText("");

		restField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		restField.setEditable(false);
		

		Server server = envService.getServerByUID( serverUID);
		Service service = envService.getServiceByUID( server.getID(), serviceUID );
		RestService srv = (RestService) service;
		restField.setText(srv.getPort());

		
	}

}
