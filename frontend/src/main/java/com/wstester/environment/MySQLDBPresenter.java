package com.wstester.environment;

import com.wstester.model.MongoService;
import com.wstester.model.MySQLService;
import com.wstester.model.Server;
import com.wstester.model.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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
	@FXML
	private Button cancel;
	@FXML
	private HBox hbox1;
	@FXML
	private GridPane gridpane;
	@FXML
	private Label labelname;
	@FXML
	private Label labeluser;
	@FXML
	private Label labelpass;
	@FXML
	private Label labelport;
	@FXML
	private Label portlabel;//need to load it so the gridpane is not empty when you set the childrens in setMySQLDB
	
	private EnvironmentService envService;
	private MainPresenter mainPresenter;
	private String uid = null;
	
	public void setMySQLDB(final String serverUID, final String serviceUID) {
		// removing FXML childrens to set them
		hbox1.getChildren().remove(save);
		hbox1.getChildren().remove(cancel);
		hbox1.getChildren().remove(edit);
		gridpane.getChildren().remove(portField);
		gridpane.getChildren().remove(dbNameField);
		gridpane.getChildren().remove(dbUsernameField);
		gridpane.getChildren().remove(dbPassField);
		gridpane.getChildren().remove(labelname);
		gridpane.getChildren().remove(labeluser);
		gridpane.getChildren().remove(labelpass);
		gridpane.getChildren().remove(labelport);
		// adding the needed childrens
		hbox1.getChildren().add(edit);
		gridpane.getChildren().add(labelname);
		gridpane.getChildren().add(labeluser);
		gridpane.getChildren().add(labelpass);
		gridpane.getChildren().add(labelport);
		Server server = envService.getServerByUID(serverUID);
		Service service = envService
				.getServiceByUID(server.getID(), serviceUID);
		MySQLService srv = (MySQLService) service;
		uid = serviceUID;
		labelport.setText(srv.getPort());
		labelname.setText(srv.getDbName());
		labeluser.setText(srv.getUser());
		labelpass.setText(srv.getPassword());
		portField.setText(srv.getPort());
		dbNameField.setText(srv.getDbName());
		dbUsernameField.setText(srv.getUser());
		dbPassField.setText(srv.getPassword());

	}

	public void saveMySQL(ActionEvent e) {

		MySQLService sql = new MySQLService();
		sql.setDbName(dbNameField.getText());
		sql.setPassword(dbPassField.getText());
		sql.setUser(dbUsernameField.getText());
		sql.setPort(portField.getText());
		envService.setMySQLServiceByUID(sql, uid);
		hbox1.getChildren().add(edit);
		hbox1.getChildren().remove(save);
		hbox1.getChildren().remove(cancel);
    	gridpane.getChildren().remove(dbNameField);
    	gridpane.getChildren().remove(dbUsernameField);
    	gridpane.getChildren().remove(dbPassField);
    	gridpane.getChildren().remove(portField);
    	gridpane.getChildren().add(labelport);
    	gridpane.getChildren().add(labelname);
    	gridpane.getChildren().add(labeluser);
    	gridpane.getChildren().add(labelpass);
    	labelport.setText(portField.getText());
    	labelname.setText(dbNameField.getText());
    	labeluser.setText(dbUsernameField.getText());   	
    	labelpass.setText(dbPassField.getText());
    	envService.saveEnv();
	}

	public void editMySQL(ActionEvent e) {
		gridpane.getChildren().add(portField);
		gridpane.getChildren().add(dbNameField);
		gridpane.getChildren().add(dbUsernameField);
		gridpane.getChildren().add(dbPassField);
		gridpane.getChildren().remove(labelport);
		gridpane.getChildren().remove(labelname);
		gridpane.getChildren().remove(labeluser);
		gridpane.getChildren().remove(labelpass);

		hbox1.getChildren().remove(edit);
		
		hbox1.getChildren().add(save);
		hbox1.getChildren().add(cancel);
	}

	public void setEnvironmentService(EnvironmentService envService) {
		this.envService = envService;
	}

	public void setMainPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	public Node getView() {
		return rootMysqlDB;
	}

	
	
	public void cancelEdit(ActionEvent event)
    {
		dbNameField.setText(labelname.getText());
		portField.setText(labelport.getText());
    	dbUsernameField.setText(labeluser.getText());
    	dbPassField.setText(labelpass.getText());
    	hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().remove(portField);
    	gridpane.getChildren().remove(dbNameField);
    	gridpane.getChildren().remove(dbUsernameField);
    	gridpane.getChildren().remove(dbPassField);
    	gridpane.getChildren().add(labelport);
    	gridpane.getChildren().add(labelname);
    	gridpane.getChildren().add(labeluser);
    	gridpane.getChildren().add(labelpass);
    }

}
