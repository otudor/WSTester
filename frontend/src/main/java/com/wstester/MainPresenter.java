package com.wstester;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MainPresenter
{
    @FXML private Parent root;
    @FXML private BorderPane contentArea;

    private EnvironmentSearchPresenter envSearchPresenter;
    private EnvironmentDetailPresenter envDetailPresenter;
    private ServerDetailsPresenter ftpDetailPresenter;

    public void setContactSearchPresenter(EnvironmentSearchPresenter envSearchPresenter)
    {
        this.envSearchPresenter = envSearchPresenter;
    }

    public void setContactDetailPresenter(EnvironmentDetailPresenter envDetailPresenter)
    {
        this.envDetailPresenter = envDetailPresenter;
    }

    public void setFTPDetailPresenter(ServerDetailsPresenter ftpDetailPresenter)
    {
        this.ftpDetailPresenter = ftpDetailPresenter;
    }
    
    public void setFTPServerDetailPresenter(ServerDetailsPresenter ftpDetailPresenter)
    {
        this.ftpDetailPresenter = ftpDetailPresenter;
    }
    
    public Parent getView()
    {
        return root;
    }

    public void loadEnvironments()
    {
        envSearchPresenter.search(null);
        envSearchPresenter.loadTreeItems();
        contentArea.setLeft( envSearchPresenter.getView());
        //contentArea.setRight( envSearchPresenter.getTree());
        
    }

    public void showEnvDetail( String envUID)
    {
        envDetailPresenter.setEnvironment( envUID);
        contentArea.setCenter( envDetailPresenter.getView());
    }
    
    public void showFTPDetail( String serverUID)
    {
    	ftpDetailPresenter.setFTP( serverUID);
        contentArea.setCenter( ftpDetailPresenter.getView());
    }
    
    public String getFirstEnvironment()
    {
    	return envSearchPresenter.getFirstEnv();
    }

}