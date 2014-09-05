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
	@FXML private GridPane gridmongo;
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
    	hbox1.getChildren().remove(save);
    	gridmongo.getChildren().remove(cancel);
    	gridmongo.getChildren().remove(MongoName);
    	gridmongo.getChildren().remove(MongoPort);
    	gridmongo.getChildren().remove(MongoUser);
    	gridmongo.getChildren().remove(MongoPassfield);
                     
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
    	gridmongo.getChildren().add(MongoName);
    	gridmongo.getChildren().add(MongoPort);
    	gridmongo.getChildren().add(MongoUser);
    	gridmongo.getChildren().add(MongoPassfield);
    	gridmongo.getChildren().remove(labelport);
    	gridmongo.getChildren().remove(labelname);
    	gridmongo.getChildren().remove(labeluser);
    	gridmongo.getChildren().remove(labelpass);
    	
    	hbox1.getChildren().remove(edit);
    	hbox1.getChildren().add(save);
    	gridmongo.getChildren().add(cancel);
    	
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
    	gridmongo.getChildren().remove(cancel);
    	gridmongo.getChildren().remove(MongoName);
    	gridmongo.getChildren().remove(MongoPort);
    	gridmongo.getChildren().remove(MongoUser);
    	gridmongo.getChildren().remove(MongoPassfield);
    	gridmongo.getChildren().add(labelport);
    	gridmongo.getChildren().add(labelname);
    	gridmongo.getChildren().add(labeluser);
    	gridmongo.getChildren().add(labelpass);
    	
    }
    
    public void cancelEdit(ActionEvent event)
    {
    	MongoName.setText(labelname.getText());
    	MongoPort.setText(labelport.getText());
    	MongoUser.setText(labeluser.getText());
    	MongoPassfield.setText(labelpass.getText());
    	gridmongo.getChildren().remove(cancel);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().add(edit);
    	gridmongo.getChildren().remove(MongoName);
    	gridmongo.getChildren().remove(MongoPort);
    	gridmongo.getChildren().remove(MongoUser);
    	gridmongo.getChildren().remove(MongoPassfield);
    	gridmongo.getChildren().add(labelport);
    	gridmongo.getChildren().add(labelname);
    	gridmongo.getChildren().add(labeluser);
    	gridmongo.getChildren().add(labelpass);
    }
}
