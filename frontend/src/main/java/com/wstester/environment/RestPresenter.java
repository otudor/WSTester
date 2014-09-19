package com.wstester.environment;


import com.wstester.model.RestService;
import com.wstester.model.Server;
import com.wstester.model.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RestPresenter {
	@FXML
	private Node restPanel;
	@FXML
	private TextField restField;
	@FXML
	private Button save;
	@FXML
	private Button edit;
	@FXML Button cancel;
	@FXML Label labelport;
	@FXML HBox hbox1;
	@FXML GridPane gridpane;
	
	private String uid = null;
	private EnvironmentService envService;
	private MainPresenter mainPresenter;
	
public void setRest( final String serverUID, final String serviceUID) 
	{
		hbox1.getChildren().remove(save);
		hbox1.getChildren().remove(cancel);
		hbox1.getChildren().remove(edit);
		gridpane.getChildren().remove(restField);
		gridpane.getChildren().remove(labelport);
		
		hbox1.getChildren().add(edit);
		gridpane.getChildren().add(labelport);
		Server server = envService.getServerByUID( serverUID);
		Service service = envService.getServiceByUID( server.getID(), serviceUID );
		RestService srv = (RestService) service;
		uid = serviceUID;
		restField.setText(srv.getPort());
		labelport.setText(srv.getPort());
		
	}

	public void saveRest(ActionEvent e) {
		
		RestService rst = new RestService();
		rst.setPort(restField.getText());
		envService.setRestServiceByUID(rst,uid);
    	hbox1.getChildren().add(edit);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	gridpane.getChildren().remove(restField);
    	gridpane.getChildren().add(labelport);
    	labelport.setText(restField.getText());
    	envService.saveEnv();
	}
	
	public void editRest(ActionEvent e) {
		
		gridpane.getChildren().add(restField);
		gridpane.getChildren().remove(labelport);
		hbox1.getChildren().remove(edit);
    	
    	hbox1.getChildren().add(save);
    	hbox1.getChildren().add(cancel);
	}
	
	public void cancelEdit(ActionEvent e)
	{
		restField.setText(labelport.getText());
		hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().remove(restField);
    	gridpane.getChildren().add(labelport);
	}

	public void setEnvironmentService(EnvironmentService envService) {
		this.envService = envService;
	}

	public void setMainPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	public Node getView() {
		return restPanel;
	}

	
	

}
