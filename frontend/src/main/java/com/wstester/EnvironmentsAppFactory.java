package com.wstester;
import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class EnvironmentsAppFactory
{
    private MainPresenter mainPresenter;
    private EnvironmentSearchPresenter envSearchPresenter;
    private EnvironmentDetailPresenter envDetailPresenter;
    private ServerDetailsPresenter ftpDetailPresenter;
    private EnvironmentService environmentService;
    private ServerService ftpService;
    private MongoDBPresenter mngDBPresenter;
    
    public MainPresenter getMainPresenter()
    {
        if (mainPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/EnvironmentManager.fxml"));
                mainPresenter = (MainPresenter) loader.getController();
                mainPresenter.setEnvironmentDetailPresenter( getEnvironmentDetailPresenter());
                mainPresenter.setEnvironmentSearchPresenter( getEnvironmentSearchPresenter());
                mainPresenter.setFTPDetailPresenter( getFTPServerDetailPresenter());
                mainPresenter.setMongoDBPresenter( getMongoDBPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load Main.fxml", e);
            }
        }
        return mainPresenter;
    }

    public EnvironmentSearchPresenter getEnvironmentSearchPresenter()
    {
        if (envSearchPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/EnvironmentSearch.fxml"));
                envSearchPresenter = (EnvironmentSearchPresenter) loader.getController();
                envSearchPresenter.setEnvService(getEnvironmentService());
                envSearchPresenter.setMainPresenter(getMainPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load EnvironmentSearch.fxml", e);
            }
        }
        return envSearchPresenter;
    }

    public ServerDetailsPresenter getFTPServerDetailPresenter()
    {
        if (ftpDetailPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/ServerDetails.fxml"));
                ftpDetailPresenter = ( ServerDetailsPresenter) loader.getController();
                ftpDetailPresenter.setService( getServerService());
                ftpDetailPresenter.setMainPresenter(getMainPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load ServerDetails.fxml", e);
            }
        }
        return ftpDetailPresenter;
    }

    public EnvironmentDetailPresenter getEnvironmentDetailPresenter()
    {
        if (envDetailPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/EnvironmentDetail.fxml"));
                envDetailPresenter = (EnvironmentDetailPresenter) loader.getController();
                envDetailPresenter.setEnvironmentService( getEnvironmentService());
                envDetailPresenter.setMainPresenter(getMainPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load EnvironmentDetail.fxml", e);
            }
        }
        return envDetailPresenter;
    }
    
    public MongoDBPresenter getMongoDBPresenter()
    {
        if (mngDBPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/MongoDB.fxml"));
                mngDBPresenter = (MongoDBPresenter) loader.getController();
                mngDBPresenter.setEnvironmentService(getEnvironmentService());
                mngDBPresenter.setMainPresenter(getMainPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load MongoDB.fxml", e);
            }
        }
        return mngDBPresenter;
    }
    
    public EnvironmentService getEnvironmentService()
    {
        if (environmentService == null)
        {
        	environmentService = new EnvironmentService();
        }
        return environmentService;
    }
    
    public ServerService getServerService()
    {
        if (ftpService == null)
        {
        	ftpService = new ServerService();
        }
        return ftpService;
    }
}