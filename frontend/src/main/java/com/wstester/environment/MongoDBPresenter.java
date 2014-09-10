package com.wstester.environment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.Server;
import com.wstester.model.Service;
public class MongoDBPresenter {
	@FXML private Node rootMongoDB;
	@FXML private HBox hbox1;
	@FXML private GridPane gridpane;
    @FXML private TextField MongoName;
    @FXML private TextField MongoPort;
    @FXML private TextField MongoUser;
    @FXML private TextField MongoPassfield;
    @FXML private Label labelname;
    @FXML private Label labeluser;
    @FXML private Label labelpass;
    @FXML private Label labelport;
    @FXML private Button edit;
    @FXML private Button save;
    @FXML private Button cancel;
    
    
    private String uid = null;
    private EnvironmentService envService;
    private MainPresenter mainPresenter;
    

    public void setEnvironmentService(EnvironmentService environmentService)
    {
        this.envService = environmentService;
    }

    public void setMainPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public Node getView()
    {
        return rootMongoDB;
    }
    
    public void setMongoDB(final String serverUID, final String serviceUID)
    {	
    	//removing FXML childrens to set them
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(edit);
    	gridpane.getChildren().remove(MongoName);
    	gridpane.getChildren().remove(MongoPort);
    	gridpane.getChildren().remove(MongoUser);
    	gridpane.getChildren().remove(MongoPassfield);
    	gridpane.getChildren().remove(labelname);
    	gridpane.getChildren().remove(labeluser);
    	gridpane.getChildren().remove(labelpass);
    	gridpane.getChildren().remove(labelport);
    	//adding the needed childrens
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().add(labelname);
    	gridpane.getChildren().add(labeluser);
    	gridpane.getChildren().add(labelpass);
    	gridpane.getChildren().add(labelport);
                     
    	Server server = envService.getServerByUID( serverUID);
		Service service = envService.getServiceByUID( server.getID(), serviceUID );
		uid = serviceUID;
		MongoService mng = (MongoService) service;
		labelport.setText( mng.getPort());
		labelname.setText( mng.getDbName());
		labeluser.setText( mng.getUser());
		labelpass.setText( mng.getPassword());
		MongoName.setText(mng.getDbName());
    	MongoPort.setText(mng.getPort());
    	MongoUser.setText(mng.getUser());
    	MongoPassfield.setText(mng.getPassword());
    }
    
    public void seteditable(ActionEvent event)
    {
    	gridpane.getChildren().add(MongoName);
    	gridpane.getChildren().add(MongoPort);
    	gridpane.getChildren().add(MongoUser);
    	gridpane.getChildren().add(MongoPassfield);
    	gridpane.getChildren().remove(labelport);
    	gridpane.getChildren().remove(labelname);
    	gridpane.getChildren().remove(labeluser);
    	gridpane.getChildren().remove(labelpass);
    	
    	hbox1.getChildren().remove(edit);
    	
    	hbox1.getChildren().add(save);
    	hbox1.getChildren().add(cancel);
    	
    }
    public void setuneditable(ActionEvent event)
    {
    	    
    	MongoService mng = new MongoService();
    	mng.setDbName(MongoName.getText());
    	mng.setPassword(MongoPassfield.getText());
    	mng.setUser(MongoUser.getText());
    	mng.setPort(MongoPort.getText());
    	envService.setMongoServiceByUID(mng, uid);
    	hbox1.getChildren().add(edit);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	gridpane.getChildren().remove(MongoName);
    	gridpane.getChildren().remove(MongoPort);
    	gridpane.getChildren().remove(MongoUser);
    	gridpane.getChildren().remove(MongoPassfield);
    	gridpane.getChildren().add(labelport);
    	gridpane.getChildren().add(labelname);
    	gridpane.getChildren().add(labeluser);
    	gridpane.getChildren().add(labelpass);
    	labelport.setText(MongoPort.getText());
    	labelname.setText(MongoName.getText());
    	labeluser.setText(MongoUser.getText());   	
    	labelpass.setText(MongoPassfield.getText());
    }
    
    public void cancelEdit(ActionEvent event)
    {
    	MongoName.setText(labelname.getText());
    	MongoPort.setText(labelport.getText());
    	MongoUser.setText(labeluser.getText());
    	MongoPassfield.setText(labelpass.getText());
    	hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().remove(MongoName);
    	gridpane.getChildren().remove(MongoPort);
    	gridpane.getChildren().remove(MongoUser);
    	gridpane.getChildren().remove(MongoPassfield);
    	gridpane.getChildren().add(labelport);
    	gridpane.getChildren().add(labelname);
    	gridpane.getChildren().add(labeluser);
    	gridpane.getChildren().add(labelpass);
    }
}
