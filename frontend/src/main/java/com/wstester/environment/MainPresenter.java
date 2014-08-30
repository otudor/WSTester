package com.wstester.environment;
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
    private MongoDBPresenter mngDBPresenter;
    private MySQLDBPresenter mysqlDBPresenter;

    public void setEnvironmentSearchPresenter(EnvironmentSearchPresenter envSearchPresenter)
    {
        this.envSearchPresenter = envSearchPresenter;
    }

    public void setEnvironmentDetailPresenter(EnvironmentDetailPresenter envDetailPresenter)
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
    
    public void setMongoDBPresenter(MongoDBPresenter mngDBPresenter)
    {
        this.mngDBPresenter = mngDBPresenter;
    }
    
    public void setMySQLPresenter(MySQLDBPresenter mysqlDBPresenter)
    {
        this.mysqlDBPresenter = mysqlDBPresenter;
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
    
    public void showMongoDb( String mongoUID)
    {
	  	mngDBPresenter.setMongoDB( mongoUID);
        contentArea.setCenter( mngDBPresenter.getView());
    }
    
    public void showMySQLDb( String mysqlUID)
    {
	  	mysqlDBPresenter.setMySQLDB( mysqlUID);
        contentArea.setCenter( mysqlDBPresenter.getView());
    }
    
    public String getFirstEnvironment()
    {
    	return envSearchPresenter.getFirstEnv();
    }

}