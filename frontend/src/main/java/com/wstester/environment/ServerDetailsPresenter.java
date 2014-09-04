package com.wstester.environment;
import com.wstester.model.MySQLService;
import com.wstester.model.Server;
import com.wstester.model.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class ServerDetailsPresenter
{
    @FXML private Node rootFTPDetails;
    @FXML private TextField ftpNameField;
    @FXML private TextField ftpIPField;
    @FXML private TextField DescField;
    
    private EnvironmentService envService;
    private MainPresenter mainPresenter;
    private String uid=null;
    public void setEnvService( EnvironmentService envService)
    {
        this.envService = envService;
    }

    public void setMainPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public Node getView()
    {
        return rootFTPDetails;
    }

    public void setFTP(final String serverUID)
    {
        //this.serverUID = serverUID;
        ftpNameField.setText("");
        ftpIPField.setText("");
        DescField.setText("");
        ftpNameField.setEditable(false);
        ftpIPField.setEditable(false);
        ftpNameField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
        ftpIPField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
        DescField.setEditable(false);
        DescField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
        uid=serverUID;
        Server server = envService.getServerByUID( serverUID);
        ftpNameField.setText( server.getName());
        ftpIPField.setText( server.getIp());
        DescField.setText(server.getDescription());
    }
    
    public void EditServer(ActionEvent e) {
		//Save.setDisable(false);
		
    	ftpNameField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	ftpNameField.setEditable(true);
    	ftpIPField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	ftpIPField.setEditable(true);
    	DescField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95);");
    	DescField.setEditable(true);
		
		
	}
    
    public void SaveServer(ActionEvent e) {
		//save.setDisable(true);
    	ftpNameField.setEditable(false);
        ftpIPField.setEditable(false);
        ftpNameField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
        ftpIPField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
        DescField.setEditable(false);
        DescField.setStyle("-fx-background-color: rgba(200, 200, 200, 1);");
        Server srv = new Server();
		srv.setName(ftpNameField.getText());
		srv.setIp(ftpIPField.getText());
		srv.setDescription(DescField.getText());		
    	envService.setServerByUID(srv,uid);
		

	}
    
    
}