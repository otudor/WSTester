package com.wstester.environment;

import com.wstester.model.TestProject;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class MainPresenter
{
    @FXML private Parent root;
    @FXML private BorderPane contentArea;

    private EnvironmentSearchPresenter envSearchPresenter;
    private EnvironmentDetailPresenter envDetailPresenter;
    private ServerDetailsPresenter ftpDetailPresenter;
    private MongoDBPresenter mngDBPresenter;
    private MySQLDBPresenter mysqlDBPresenter;
    private SoapPresenter soapPresenter;
    private RestPresenter rstPresenter;
    public TestProject testProject;
    private EnvironmentService envService;

    public TestProject getTestProject() {
		return testProject;
	}

	public void setTestProject(TestProject testproject) {
		this.testProject = testproject;
	}

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
    
    public void setSoapPresenter(SoapPresenter soapPresenter)
    {
        this.soapPresenter = soapPresenter;
    }
    
    public void setRestPresenter(RestPresenter rstPresenter)
    {
        this.rstPresenter = rstPresenter;
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
    
    public void showMongoDb( String serverUID,String mongoUID)
    {
	  	mngDBPresenter.setMongoDB( serverUID,mongoUID);
        contentArea.setCenter( mngDBPresenter.getView());
    }
    
    public void showMySQLDb(String serverUID, String mysqlUID)
    {
	  	mysqlDBPresenter.setMySQLDB( serverUID, mysqlUID);
        contentArea.setCenter( mysqlDBPresenter.getView());
    }
    
    public void showSoap(String serverUID, String soaplUID)
    {
    	soapPresenter.setSoapWindow( serverUID, soaplUID);
        contentArea.setCenter( soapPresenter.getView());
    }
    
    public void showRest(String serverUID, String rstUID)
    {
	  	rstPresenter.setRest( serverUID, rstUID);
        contentArea.setCenter( rstPresenter.getView());
    }
    
    public String getFirstEnvironment()
    {
    	return envSearchPresenter.getFirstEnv();
    }

}