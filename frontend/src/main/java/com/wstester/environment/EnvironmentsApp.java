package com.wstester.environment;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EnvironmentsApp extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }
    
    public void start(Stage stage) throws Exception
    {
        EnvironmentsAppFactory factory = new EnvironmentsAppFactory();
        MainPresenter mainPresenter = factory.getMainPresenter();
        mainPresenter.loadEnvironments();
        mainPresenter.showEnvDetail( mainPresenter.getFirstEnvironment());
        Scene scene = new Scene(mainPresenter.getView(), 1000, 700);
        //scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.setTitle("First Old - JavaFX Old Management");
        stage.show();
    }
}