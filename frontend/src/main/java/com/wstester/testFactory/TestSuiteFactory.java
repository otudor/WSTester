package com.wstester.testFactory;

import java.io.IOException;




import com.wstester.util.TestProjectService;

import javafx.fxml.FXMLLoader;

public class TestSuiteFactory
{
    private TestSuiteManagerController tsManagerController;
    private TestSuiteListController tsListController;
    private TestSuiteController tSuiteController;
    private TestCaseController tCaseController;
    private MySQLStepController mySQLStepController;
    private MongoStepController mongoStepController;
    private SoapStepController soapStepController;
    private RestStepController restStepController;
    private TestProjectService tsService;
    private ResponseController responseController;
    
    public void scrie(String scrie)
	 {
		 System.out.println(scrie);
	 }
    
    
    
    
    public TestSuiteManagerController getManagerController()
    {
        if (tsManagerController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/TestSuiteManager.fxml"));
                tsManagerController = (TestSuiteManagerController) loader.getController();
                tsManagerController.setTestSuiteDetailController( getTestSuiteController());
                tsManagerController.setTestSuiteListController( getTestSuiteListController());
                tsManagerController.setTestCaseDetailController( getTestCaseController());
                tsManagerController.setMySQLStepController( getMySQLStepController());
                tsManagerController.setMongoStepController(getMongoStepController());
                tsManagerController.setSoapStepController(getSoapStepController());
                tsManagerController.setRestStepController(getRestStepController());
//                tsManagerController.setEmptyTabController(getEmptyTabController());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load TestSuiteManager.fxml", e);
            }
        }
        return tsManagerController;
    }

    public TestSuiteListController getTestSuiteListController()
    {
        if (tsListController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/TestSuiteList.fxml"));
                tsListController = (TestSuiteListController) loader.getController();
//                tsListController.setTestSuiteService( getTestSuiteService());
                tsListController.setTestSuiteManagerController( getManagerController());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load TestSuiteList.fxml", e);
            }
        }
        return tsListController;
    }

    public ResponseController getResponseTabController()
    {
        if (responseController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/Response.fxml"));
                responseController = (ResponseController) loader.getController();
//                responseTabController.setTestSuiteService( getTestSuiteService());
//                responseTabController.setTestSuiteManagerController( getManagerController());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load TestSuiteList.fxml", e);
            }
        }
        return responseController;
    }
    
    public TestCaseController getTestCaseController()
    {
        if (tCaseController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/TestCase.fxml"));
                tCaseController = ( TestCaseController) loader.getController();
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load TestCase.fxml", e);
            }
        }
        return tCaseController;
    }

    public MySQLStepController getMySQLStepController()
    {
        if (mySQLStepController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/fxml/TestFactory/MySQLStep.fxml"));
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/MySQLStep.fxml"));
                mySQLStepController = (MySQLStepController) loader.getController();
                mySQLStepController.setTestSuiteService( getTestSuiteService());
                mySQLStepController.setTestSuiteManagerController( getManagerController());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load MySQLStep.fxml", e);
            }
        }
        return mySQLStepController;
    }
    public MongoStepController getMongoStepController()
    {
        if (mongoStepController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/MongoStep.fxml"));
                mongoStepController = (MongoStepController) loader.getController();
                mongoStepController.setTestSuiteService( getTestSuiteService());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load MongoStep.fxml", e);
            }
        }
        return mongoStepController;
    }
    
    public SoapStepController getSoapStepController()
    {
        if (soapStepController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/SoapStep.fxml"));
                soapStepController = (SoapStepController) loader.getController();
                soapStepController.setTestSuiteService( getTestSuiteService());
                soapStepController.setTestSuiteManagerController( getManagerController());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load SoapStep.fxml", e);
            }
        }
        return soapStepController;
    }
    
    public RestStepController getRestStepController()
    {
        if (restStepController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/fxml/TestFactory/RestStep.fxml"));
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/RestStep.fxml"));
                restStepController = (RestStepController) loader.getController();
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load RestStep.fxml", e);
            }
        }
        return restStepController;
    }
    
    public TestSuiteController getTestSuiteController()
    {
        if (tSuiteController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/TestSuite.fxml"));
                tSuiteController = (TestSuiteController) loader.getController();
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load TestSuite.fxml", e);
            }
        }
        return tSuiteController;
    }
    
    public TestProjectService getTestSuiteService()
    {
        if (tsService == null)
        {
        	tsService = new TestProjectService();
        }
        return tsService;
    }
}