package com.wstester.environment;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class EnvironmentsAppFactory
{
    private MainPresenter mainPresenter;
    private EnvironmentSearchPresenter envSearchPresenter;
    private EnvironmentDetailPresenter envDetailPresenter;
    private ServerDetailsPresenter ftpDetailPresenter;
    private EnvironmentService environmentService;
    private MongoDBPresenter mngDBPresenter;
    private MySQLDBPresenter mysqlDBPresenter;
    private SoapPresenter soapPresenter;
    private RestPresenter rstPresenter;
    
    public MainPresenter getMainPresenter()
    {
        if (mainPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/environment/EnvironmentManager.fxml"));
                mainPresenter = (MainPresenter) loader.getController();
                mainPresenter.setEnvironmentDetailPresenter( getEnvironmentDetailPresenter());
                mainPresenter.setEnvironmentSearchPresenter( getEnvironmentSearchPresenter());
                mainPresenter.setFTPDetailPresenter( getFTPServerDetailPresenter());
                mainPresenter.setMongoDBPresenter( getMongoDBPresenter());
                mainPresenter.setMySQLPresenter( getMySQLPresenter());
                mainPresenter.setSoapPresenter( getSoapPresenter());
                mainPresenter.setRestPresenter( getRestPresenter());
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
                loader.load(getClass().getResourceAsStream("/fxml/environment/EnvironmentSearch.fxml"));
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
                loader.load(getClass().getResourceAsStream("/fxml/environment/ServerDetails.fxml"));
                ftpDetailPresenter = ( ServerDetailsPresenter) loader.getController();
                ftpDetailPresenter.setEnvService( getEnvironmentService());
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
                loader.load(getClass().getResourceAsStream("/fxml/environment/EnvironmentDetail.fxml"));
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
                loader.load(getClass().getResourceAsStream("/fxml/environment/MongoDB.fxml"));
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
    
    public MySQLDBPresenter getMySQLPresenter()
    {
        if (mysqlDBPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/environment/MysqlWindow.fxml"));
                mysqlDBPresenter = (MySQLDBPresenter) loader.getController();
                mysqlDBPresenter.setEnvironmentService(getEnvironmentService());
                mysqlDBPresenter.setMainPresenter(getMainPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load MysqlDB.fxml", e);
            }
        }
        return mysqlDBPresenter;
    }
    
    public SoapPresenter getSoapPresenter()
    {
        if (soapPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/environment/SoapWindow.fxml"));
                soapPresenter = (SoapPresenter) loader.getController();
                soapPresenter.setEnvironmentService(getEnvironmentService());
                soapPresenter.setMainPresenter(getMainPresenter());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load SoapWindow.fxml", e);
            }
        }
        return soapPresenter;
    }
    
    public RestPresenter getRestPresenter()
    {
        if (rstPresenter == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/environment/EnvRest.fxml"));
                rstPresenter = (RestPresenter) loader.getController();
                rstPresenter.setEnvironmentService(getEnvironmentService());
                rstPresenter.setMainPresenter(getMainPresenter());
            }
            catch (Exception e)
            {
                throw new RuntimeException("Unable to load EnvRest.fxml", e);
            }
        }
        return rstPresenter;
    }
    
    public EnvironmentService getEnvironmentService()
    {
        if (environmentService == null)
        {
        	environmentService = new EnvironmentService();
        }
        return environmentService;
    }
}