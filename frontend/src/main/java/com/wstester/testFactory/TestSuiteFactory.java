package com.wstester.testFactory;
import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class TestSuiteFactory
{
    private TestSuiteManagerController tsManagerController;
    private TestSuiteListController tsListController;
    private TestSuiteController tSuiteController;
    private TestCaseController tCaseController;
    private TestStepController tStepController;
    private TestSuiteService tsService;
    
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
                tsListController.setTestSuiteService( getTestSuiteService());
                tsListController.setTestSuiteManagerController( getManagerController());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load TestSuiteList.fxml", e);
            }
        }
        return tsListController;
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
                tCaseController.setTestSuiteService( getTestSuiteService());
                tCaseController.setTestSuiteManagerController( getManagerController());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load TestCase.fxml", e);
            }
        }
        return tCaseController;
    }

    public TestStepController getTestStepController()
    {
        if (tStepController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.load(getClass().getResourceAsStream("/fxml/TestFactory/TestStep.fxml"));
                tStepController = (TestStepController) loader.getController();
                tStepController.setTestSuiteService( getTestSuiteService());
                tStepController.setTestSuiteManagerController( getManagerController());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load TestStep.fxml", e);
            }
        }
        return tStepController;
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
                tSuiteController.setTestSuiteService( getTestSuiteService());
                tSuiteController.setMainPresenter(getManagerController());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Unable to load TestSuite.fxml", e);
            }
        }
        return tSuiteController;
    }
    
    public TestSuiteService getTestSuiteService()
    {
        if (tsService == null)
        {
        	tsService = new TestSuiteService();
        }
        return tsService;
    }
}