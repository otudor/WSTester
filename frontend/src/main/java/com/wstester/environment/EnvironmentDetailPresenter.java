package com.wstester.environment;

import com.wstester.model.Environment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EnvironmentDetailPresenter {
	@FXML private Node rootEnvDetails;
	@FXML private TextField envNameField;
	@FXML private Label labelname;
	@FXML HBox hbox1;
	@FXML GridPane gridpane;
	@FXML private Button edit;
	@FXML private Button save;
	@FXML private Button cancel;
	
	private String uid;

	private EnvironmentService environmentService;
	private MainPresenter mainPresenter;
	
	public void setEnvironment(final String envUID)	{
		
		hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(edit);
    	gridpane.getChildren().remove(envNameField);
    	gridpane.getChildren().remove(labelname);
    	
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().add(labelname);
		Environment env = environmentService.getEnvironment( envUID);
		envNameField.setText( env.getName());
		labelname.setText( env.getName());
		uid=envUID;
		
		
	}

	public void editEnv(ActionEvent e){
		
		gridpane.getChildren().remove(labelname);
		gridpane.getChildren().add(envNameField);
		hbox1.getChildren().remove(edit);
    	hbox1.getChildren().add(cancel);
    	hbox1.getChildren().add(save);
    	
		}
	public void saveEnv(ActionEvent e){
		
		Environment env = new Environment();
		env.setName(envNameField.getText());
		environmentService.setEnvNameByUID(env.getName(),uid);
		hbox1.getChildren().add(edit);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	gridpane.getChildren().add(labelname);
    	gridpane.getChildren().remove(envNameField);
    	labelname.setText(envNameField.getText());
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
	
	public void cancelEdit(ActionEvent event)
	{
		envNameField.setText(labelname.getText());
	    hbox1.getChildren().remove(cancel);
	    hbox1.getChildren().remove(save);
	    hbox1.getChildren().add(edit);
	    gridpane.getChildren().remove(envNameField);
	    gridpane.getChildren().add(labelname);
	}

	
}