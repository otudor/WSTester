package com.wstester.testFactory;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class ResponseTabController {
	
	@FXML 
	private Node rootPane;
	@FXML 
	private StackPane treePane;
	
    public Node getView() {
        return rootPane;
    }

    public void setResponseContent(String response) {
    	
		try {
			XmlParser xmlParser = new XmlParser();

			treePane.getChildren().clear();
			treePane.getChildren().add(xmlParser.getTreeViewOfXml(response));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
