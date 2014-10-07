package com.wstester.testFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class ResponseTabController {
	@FXML private Node rootPane;
	@FXML private StackPane treePane;
	
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
        return rootPane;
    }

    
    public void setResponseTabController() {

		try {
			
			XmlParser xmlParser = new XmlParser();
			FileInputStream file = new FileInputStream(new File("../backend/pom.xml"));
			treePane.getChildren().clear();
			treePane.getChildren().add((Node)xmlParser.getTreeViewOfXml(file));
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
