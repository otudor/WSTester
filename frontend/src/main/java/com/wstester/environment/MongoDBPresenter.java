package com.wstester.environment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.Server;
import com.wstester.model.Service;
public class MongoDBPresenter {
	@FXML private Node rootMongoDB;
    @FXML private TextField MongoName;
    @FXML private TextField MongoPort;
    @FXML private TextField MongoUser;
    @FXML private TextField MongoPassfield;
    
    
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
        return rootMongoDB;
    }
    
    public void setMongoDB(final String serverUID, final String serviceUID)
    {
    	MongoName.setText("");
    	MongoPort.setText("");
    	MongoUser.setText("");
    	MongoPassfield.setText("");
        
        MongoName.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	MongoPort.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	MongoUser.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	MongoPassfield.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
        
    	Server server = envService.getServerByUID( serverUID);
		Service service = envService.getServiceByUID( server.getID(), serviceUID );
    	MongoService mng = (MongoService) service;
        MongoPort.setText( mng.getPort());
        MongoName.setText( mng.getDbName());
        MongoUser.setText( mng.getUser());
        MongoPassfield.setText( mng.getPassword());
    }
    
    public void seteditable(ActionEvent event)
    {
    	MongoName.setEditable(true);
    	MongoPort.setEditable(true);
    	MongoUser.setEditable(true);
    	MongoPassfield.setEditable(true);
    	MongoName.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	MongoPort.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	MongoUser.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	MongoPassfield.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	
    }
    public void setuneditable(ActionEvent event)
    {
    	MongoName.setEditable(false);
    	MongoPort.setEditable(false);
    	MongoUser.setEditable(false);
    	MongoPassfield.setEditable(false);
    	MongoName.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	MongoPort.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	MongoUser.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    	MongoPassfield.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
    }
}
