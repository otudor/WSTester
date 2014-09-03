package com.wstester.environment;

import com.wstester.model.MongoService;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class SoapPresenter {
	@FXML private Node rootSoapService;
	@FXML private TextField SoapPath;
    @FXML private TextField SoapPort;
    @FXML private TextField SoapWsdl;

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
    	SoapPath.setText("");
    	SoapPort.setText("");
    	SoapWsdl.setText("");
    	SoapPath.setEditable(false);
    	SoapPort.setEditable(false);
    	SoapWsdl.setEditable(false);
        
    	SoapPath.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	SoapPort.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	SoapWsdl.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	
        
    	Server server = envService.getServerByUID( serverUID);
		Service service = envService.getServiceByUID( server.getID(), serviceUID );    	
		uid = serviceUID;
		SoapService soap = (SoapService) service;
		SoapPath.setText( soap.getPath());
    	SoapPort.setText( soap.getPort());
    	SoapWsdl.setText( soap.getWsdlURL());
    	
    	
    }
    
    public void seteditable(ActionEvent event)
    {
    	SoapPath.setEditable(true);
    	SoapPort.setEditable(true);
    	SoapWsdl.setEditable(true);
    	
    	SoapPath.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	SoapPort.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	SoapWsdl.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	    	
    }
    
    public void setuneditable(ActionEvent event)
    {
    	SoapPath.setEditable(false);
    	SoapPort.setEditable(false);
    	SoapWsdl.setEditable(false);
    	
    	SoapPath.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	SoapPort.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	SoapWsdl.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	SoapService sop = new SoapService();
    	sop.setWsdlURL(SoapWsdl.getText());
    	sop.setPath(SoapPath.getText());
    	sop.setPort(SoapPort.getText());
    	envService.setSoapServiceByUID(sop, uid);
    }
    
}
