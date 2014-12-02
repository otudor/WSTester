package com.wstester.environment;

import com.wstester.model.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ServerDetailsPresenter
{
    @FXML private Node rootFTPDetails;
   // @FXML private TextField ftpNameField;
    @FXML private TextField ftpIPField;
    @FXML private TextField DescField;
    @FXML private TextField ftpNameField;
    @FXML private Label labelname;
    @FXML private Label labelip;
    @FXML private Label labeldesc;
    @FXML private HBox hbox1;
	@FXML private GridPane gridpane;
	@FXML private Button edit;
	@FXML private Button save;
	@FXML private Button cancel;
   
	private EnvironmentService envService;
    private MainPresenter mainPresenter;
    private String uid=null;
    
    public void setEnvService( EnvironmentService envService)
    {
        this.envService = envService;
    }

    public void setMainPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public Node getView()
    {
        return rootFTPDetails;
    }

    public void setFTP(final String serverUID)
    {
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(edit);
    	gridpane.getChildren().remove(ftpIPField);
    	gridpane.getChildren().remove(DescField);
    	gridpane.getChildren().remove(ftpNameField);
    	gridpane.getChildren().remove(labelname);
    	gridpane.getChildren().remove(labelip);
    	gridpane.getChildren().remove(labeldesc);
    	 
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().add(labelname);
    	gridpane.getChildren().add(labelip);
    	gridpane.getChildren().add(labeldesc);

    	uid=serverUID;
        Server server = envService.getServerByUID( serverUID);
        ftpNameField.setText( server.getName());
        ftpIPField.setText( server.getIp());
        DescField.setText(server.getDescription());
        labelname.setText( server.getName());
        labelip.setText( server.getIp());
        labeldesc.setText(server.getDescription());
        
    }
    
    public void EditServer(ActionEvent e) {
		//Save.setDisable(false);
    	gridpane.getChildren().add(ftpNameField);
    	gridpane.getChildren().add(DescField);
    	gridpane.getChildren().add(ftpIPField);
    	gridpane.getChildren().remove(labelname);
    	gridpane.getChildren().remove(labelip);
    	gridpane.getChildren().remove(labeldesc);
       	
    	hbox1.getChildren().remove(edit);
    	
    	hbox1.getChildren().add(save);
    	hbox1.getChildren().add(cancel);
		
		
	}
    
    public void SaveServer(ActionEvent e) {
		//save.setDisable(true);
    	
        Server srv = new Server();
		srv.setName(ftpNameField.getText());
		srv.setIp(ftpIPField.getText());
		srv.setDescription(DescField.getText());		
    	envService.setServerByUID(srv,uid);
    	hbox1.getChildren().add(edit);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	gridpane.getChildren().remove(ftpNameField);
    	gridpane.getChildren().remove(DescField);
    	gridpane.getChildren().remove(ftpIPField);
    	gridpane.getChildren().add(labelname);
    	gridpane.getChildren().add(labelip);
    	gridpane.getChildren().add(labeldesc);
    	labelname.setText(ftpNameField.getText());
    	labelip.setText(ftpIPField.getText());   	
    	labeldesc.setText(DescField.getText());		
    	envService.saveEnv();
    	mainPresenter.loadEnvironments();
	}
    
    public void cancelEdit(ActionEvent e)
    {
    	ftpNameField.setText(labelname.getText());
    	ftpIPField.setText(labelip.getText());
    	DescField.setText(labeldesc.getText());
    	hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().remove(ftpNameField);
    	gridpane.getChildren().remove(DescField);
    	gridpane.getChildren().remove(ftpIPField);
      	gridpane.getChildren().add(labelname);
    	gridpane.getChildren().add(labelip);
    	gridpane.getChildren().add(labeldesc);
    }
    
}