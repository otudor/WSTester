package com.wstester;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.Server;

public class MySQLDBPresenter {
	@FXML
	private Node rootMysqlDB;
	@FXML
	private TextField portField;
	@FXML
	private TextField dbNameField;
	@FXML
	private TextField dbUsernameField;
	@FXML
	private PasswordField dbPassField;
	
	@FXML
	private Button save;
	@FXML
	private Button edit;
	
	public void saveMySQL( ActionEvent e){
		save.setDisable(true);
		portField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		portField.setEditable(false);
		dbNameField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		dbNameField.setEditable(false);
		dbUsernameField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		dbUsernameField.setEditable(false);
		dbPassField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		dbPassField.setEditable(false);
		
	}
	public void editMySQL(ActionEvent e){
		save.setDisable(false);
		portField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
		portField.setEditable(true);
		dbNameField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
		portField.setEditable(true);
		dbUsernameField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
		portField.setEditable(true);
		dbPassField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
		portField.setEditable(true);
	}
	private EnvironmentService environmentService;
	private MainPresenter mainPresenter;

	public void setEnvironmentService(EnvironmentService environmentService) {
		this.environmentService = environmentService;
	}

	public void setMainPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	public Node getView() {
		return rootMysqlDB;
	}

	public void setMySQLDB(final String serviceUID) {
		portField.setText("");
		dbNameField.setText("");
		dbUsernameField.setText("");
		dbPassField.setText("");
		
		portField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		portField.setEditable(false);
		dbNameField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		dbNameField.setEditable(false);
		dbUsernameField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		dbUsernameField.setEditable(false);
		dbPassField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
		dbPassField.setEditable(false);
		
		portField.setText("8802");
		dbNameField.setText("TestDataBase");
		dbUsernameField.setText("sudo");
		dbPassField.setText("admin");

	}

}