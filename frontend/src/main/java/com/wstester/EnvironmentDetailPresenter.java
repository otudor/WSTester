package com.wstester;

import com.wstester.model.Environment;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class EnvironmentDetailPresenter
{
    @FXML private Node rootEnvDetails;
    @FXML private TextField envNameField;

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
        return rootEnvDetails;
    }
    
    public void setEnvironment(final String envUID)
    {
        envNameField.setText("");
        
        Environment env = environmentService.getEnvironment( envUID);
        envNameField.setText( env.getName());
    }
}