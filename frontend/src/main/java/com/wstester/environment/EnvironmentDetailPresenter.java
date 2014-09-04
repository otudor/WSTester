package com.wstester.environment;

import com.wstester.model.Environment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EnvironmentDetailPresenter {
	@FXML private Node rootEnvDetails;
	@FXML private TextField envNameField;
	@FXML private Button editEnv;
	@FXML private Button saveEnv;
	private String uid;

	private EnvironmentService environmentService;
	private MainPresenter mainPresenter;
	
	public void editEnv(ActionEvent e){
		saveEnv.setDisable(false);
		envNameField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
		envNameField.setEditable(true);
	}
	public void saveEnv(ActionEvent e){
		saveEnv.setDisable(true);
		envNameField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		envNameField.setEditable(false);
		Environment env = new Environment();
		env.setName(envNameField.getText());
		environmentService.setEnvNameByUID(env.getName(),uid);
		
		
	}

	public void setEnvironmentService(EnvironmentService environmentService)	{
		this.environmentService = environmentService;
	}

	public void setMainPresenter(MainPresenter mainPresenter)	{
		this.mainPresenter = mainPresenter;
	}

	public Node getView()	{
		return rootEnvDetails;
	}

	public void setEnvironment(final String envUID)	{
		saveEnv.setDisable(false);
		
		envNameField.setEditable(false);
		envNameField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		envNameField.setText("");
		Environment env = environmentService.getEnvironment( envUID);
		envNameField.setText( env.getName());
		uid=envUID;
	}
}