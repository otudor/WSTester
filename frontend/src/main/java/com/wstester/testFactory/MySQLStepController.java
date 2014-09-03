package com.wstester.testFactory;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class MySQLStepController
{
    @FXML private Node rootMySQLStep;
    @FXML private TextField txtName;
    @FXML private TextField txtSQL;

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
        return rootMySQLStep;
    }

    public void setMySQLStep(final String stepUID)
    {
        //this.serverUID = serverUID;
    	txtName.setText("");
        txtSQL.setText("SQL");
        
        Step stp = tsService.getStep( stepUID);
        txtName.setText(((MySQLStep)stp).getName());
        txtSQL.setText(((MySQLStep)stp).getOperation());
		/*Service service = envService.getServiceByUID( server.getID(), serviceUID );
    	MongoService mng = (MongoService) service;
        MongoPort.setText( mng.getPort());
        MongoName.setText( mng.getDbName());
        MongoUser.setText( mng.getUser());
        MongoPassfield.setText( mng.getPassword());*/
        
        //Server server = tsService.getServerByUID( serverUID);
        //txtStepName.setText( tc.getName());
    }
}