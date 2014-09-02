package com.wstester.testFactory;
import com.wstester.model.Server;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class TestStepController
{
    @FXML private Node rootTStep;
    @FXML private TextField txtStepName;

    private TestSuiteService tsService;
    private TestSuiteManagerController tsMainController;

    public void setTestSuiteService( TestSuiteService tsService)
    {
        this.tsService = tsService;
    }

    public void setTestSuiteManagerController(TestSuiteManagerController tsMainController)
    {
        this.tsMainController = tsMainController;
    }

    public Node getView()
    {
        return rootTStep;
    }

    public void setTestStep(final String sUID)
    {
        /*//this.serverUID = serverUID;
        ftpNameField.setText("");
        ftpIPField.setText("");
        
        Server server = tsService.getServerByUID( serverUID);
        ftpNameField.setText( server.getName());
        ftpIPField.setText( server.getIp());*/
    }
}