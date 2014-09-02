package com.wstester.testFactory;
import com.wstester.model.Server;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class TestCaseController
{
    @FXML private Node rootTCDetails;
    @FXML private TextField ftpNameField;
    @FXML private TextField ftpIPField;

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
        return rootTCDetails;
    }

    public void setTestCase(final String tcUID)
    {
        /*//this.serverUID = serverUID;
        ftpNameField.setText("");
        ftpIPField.setText("");
        
        Server server = tsService.getServerByUID( serverUID);
        ftpNameField.setText( server.getName());
        ftpIPField.setText( server.getIp());*/
    }
}