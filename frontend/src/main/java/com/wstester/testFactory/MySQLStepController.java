package com.wstester.testFactory;
import com.sun.prism.paint.Color;
import com.wstester.actions.TestRunner;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MySQLStepController
{
    @FXML private Node rootMySQLStep;
    @FXML private Label lblName;
    @FXML private Label lblSQL;
    @FXML private Label lblStatus;
    @FXML private Label lblResponse;
    
    private MySQLStep stp;
    
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

//    public void setTxtResponse(){
//    	TestRunner testRunner = new TestRunner();
//    	Response response = null;
//    	
//    	if(stp!=null){
//    		
//    		response = testRunner.getResponse(stp.getID(), 25000L);
//    	}
//    	
//    	if( response != null)
//    	{
//    		if( response.isPass())
//    		{
//    			lblStatus.setStyle("-fx-background-color: green;");
//    			lblStatus.setText("");
//    		}
//    		else
//    			lblStatus.setStyle("-fx-background-color: gray;");
//    		this.lblResponse.setText(response.getContent());
//    	}
//    }
    
    public void setMySQLStep(final String stepUID)
    {
    	lblName.setText("");
        lblSQL.setText("SQL");
        lblStatus.setText("Not run");
        lblResponse.setText("Not run");
        
        stp = (MySQLStep) tsService.getStep( stepUID);
        lblName.setText(stp.getName());
        lblSQL.setText(stp.getOperation());
//        setTxtResponse();
    }
}