package com.wstester.testFactory;
import com.sun.prism.paint.Color;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class MySQLStepController extends BaseRunner
{
    @FXML private Node rootMySQLStep;
    @FXML private TextField txtName;
    @FXML private TextField txtSQL;
    @FXML private TextField txtResponse;
    @FXML private TextField txtStatus;
    
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

    public void setTxtResponse(){
    	
    	Response response = null;
    	if(stp!=null && testRunner!=null){
    		
    		response = testRunner.getResponse(stp.getID(), 25000L);
    	}
    	
    	if( response != null)
    	{
    		if( response.isPass())
    			txtStatus.setStyle("-fx-background-color: green;");
    		else
    			txtStatus.setStyle("-fx-background-color: gray;");
    		this.txtResponse.setText(response.getContent());
    	}
    }
    
    public void setMySQLStep(final String stepUID)
    {
    	txtName.setText("");
        txtSQL.setText("SQL");
        
        stp = (MySQLStep) tsService.getStep( stepUID);
        txtName.setText(stp.getName());
        txtSQL.setText(stp.getOperation());
        setTxtResponse();
    }
}