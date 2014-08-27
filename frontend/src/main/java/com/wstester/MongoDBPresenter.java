package com.wstester;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.Server;
public class MongoDBPresenter {
	@FXML private Node rootMongoDB;
    @FXML private TextField MongoName;
    @FXML private TextField MongoPort;
    @FXML private TextField MongoUser;
    @FXML private TextField MongoPassfield;

    private EnvironmentService environmentService;
    private MainPresenter mainPresenter;

    public void setEnvironmentService(EnvironmentService environmentService)
    {
        this.environmentService = environmentService;
    }

    public void setMainPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public Node getView()
    {
        return rootMongoDB;
    }
    
    public void setMongoDB(final String serviceUID)
    {
    	MongoName.setText("");
    	MongoPort.setText("");
    	MongoUser.setText("");
    	MongoPassfield.setText("");
        
        /*MongoService mng = new MongoService();
        MongoName.setText( mng.getPort());
        MongoPort.setText( mng.getDbName());
        MongoUser.setText( mng.getUser());
        MongoPassfield.setText( mng.getPassword());*/
    	MongoName.setText( "port");
        MongoPort.setText( "name");
        MongoUser.setText( "dfkajshdfkdsf");
        MongoPassfield.setText( "Adfsadf");
        
       /* MongoService mng = mongoService (copie dupa ftpService) .getServiceByUID( serviceUID);
        MongoName.setText( mng.getPort());
        MongoPort.setText( mng.getDbName());
        MongoUser.setText( mng.getUser());
        MongoPassfield.setText( mng.getPassword());*/
    }

}
