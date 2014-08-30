package com.wstester.environment;
import com.wstester.model.Server;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class ServerDetailsPresenter
{
    @FXML private Node rootFTPDetails;
    @FXML private TextField ftpNameField;
    @FXML private TextField ftpIPField;

    private EnvironmentService envService;
    private MainPresenter mainPresenter;

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
        
        Server server = envService.getServerByUID( serverUID);
        ftpNameField.setText( server.getName());
        ftpIPField.setText( server.getIp());
    }
}