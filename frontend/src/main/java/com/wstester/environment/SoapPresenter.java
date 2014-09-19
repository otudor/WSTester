package com.wstester.environment;

import com.wstester.model.MongoService;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SoapPresenter {
	@FXML private Node rootSoapService;
	@FXML private TextField SoapPath;
    @FXML private TextField SoapPort;
    @FXML private TextField SoapWsdl;
    @FXML private Button edit;
    @FXML private Button save;
    @FXML private Button cancel;
    @FXML private Label labelwsdl;
    @FXML private Label labelport;
    @FXML private Label labelpath;
    @FXML private HBox hbox1;
	@FXML private GridPane gridpane;
    
    private String uid = null;
    private EnvironmentService envService;
    private MainPresenter mainPresenter;

    public void setEnvironmentService(EnvironmentService environmentService)
    {
        this.envService = environmentService;
    }

    public void setMainPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public Node getView()
    {
        return rootSoapService;
    }
    
    public void setSoapWindow(final String serverUID, final String serviceUID)
    {
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(edit);
    	gridpane.getChildren().remove(SoapPath);
    	gridpane.getChildren().remove(SoapPort);
    	gridpane.getChildren().remove(SoapWsdl);
       	gridpane.getChildren().remove(labelwsdl);
    	gridpane.getChildren().remove(labelport);
    	gridpane.getChildren().remove(labelpath);
   	
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().add(labelwsdl);
    	gridpane.getChildren().add(labelport);
    	gridpane.getChildren().add(labelpath);
    	Server server = envService.getServerByUID( serverUID);
		Service service = envService.getServiceByUID( server.getID(), serviceUID );    	
		uid = serviceUID;
		SoapService soap = (SoapService) service;
		SoapPath.setText( soap.getPath());
    	SoapPort.setText( soap.getPort());
    	SoapWsdl.setText( soap.getWsdlURL());
    	labelpath.setText( soap.getPath());
		labelport.setText( soap.getPort());
    	labelwsdl.setText( soap.getWsdlURL());
    	
    	
    }
    
    public void seteditable(ActionEvent event)
    {
    	gridpane.getChildren().add(SoapPath);
    	gridpane.getChildren().add(SoapPort);
    	gridpane.getChildren().add(SoapWsdl);
    	gridpane.getChildren().remove(labelwsdl);
    	gridpane.getChildren().remove(labelport);
    	gridpane.getChildren().remove(labelpath);
    	
    	hbox1.getChildren().remove(edit);
    	
    	hbox1.getChildren().add(save);
    	hbox1.getChildren().add(cancel);
    }
    
    public void setuneditable(ActionEvent event)
    {
       	SoapService sop = new SoapService();
    	sop.setWsdlURL(SoapWsdl.getText());
    	sop.setPath(SoapPath.getText());
    	sop.setPort(SoapPort.getText());
    	envService.setSoapServiceByUID(sop, uid);
    	hbox1.getChildren().add(edit);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	gridpane.getChildren().remove(SoapPath);
    	gridpane.getChildren().remove(SoapPort);
    	gridpane.getChildren().remove(SoapWsdl);
    	gridpane.getChildren().add(labelwsdl);
    	gridpane.getChildren().add(labelport);
    	gridpane.getChildren().add(labelpath);
    	labelpath.setText(SoapPath.getText());
    	labelport.setText(SoapPort.getText());
    	labelwsdl.setText(SoapWsdl.getText());
    	envService.saveEnv();
    }
    
    public void cancelEdit(ActionEvent event)
    {
    	SoapPath.setText(labelpath.getText());
    	SoapPort.setText(labelport.getText());
    	SoapWsdl.setText(labelwsdl.getText());
    	
    	hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().remove(SoapPath);
    	gridpane.getChildren().remove(SoapPort);
    	gridpane.getChildren().remove(SoapWsdl);
    	gridpane.getChildren().add(labelport);
    	gridpane.getChildren().add(labelpath);
    	gridpane.getChildren().add(labelwsdl);
    }
    
}
