package com.wstester.testFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {
	
	public XmlParser() {
		
	}
	
	public TreeView getTreeViewOfXml(FileInputStream file) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		
		//FileInputStream file = new FileInputStream(new File("C:/Users/sdinescu/Documents/GitHub/WSTester/backend/pom.xml"));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder =  builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(file);
        
        XPath xPath =  XPathFactory.newInstance().newXPath();
        
        String expression = ".";
        Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
        
        TreeItem<Object> treeItem = new TreeItem<>();
        TreeView<Object> treeView = new TreeView<>(treeItem);
        
        if(null != node) {
         	treeItem.setExpanded(true);
        	treeView.setShowRoot(false);
        	NodeList nodeList = node.getChildNodes();
            addToTreeItem(treeItem, nodeList);
        }
        return treeView;
	}
        void addToTreeItem(TreeItem<Object> Item, NodeList nodeList) {
   		 for(int i = 0;null!=nodeList && i < nodeList.getLength(); i++) {
              Node nod = nodeList.item(i);
              TreeItem<Object> treeItem = new TreeItem<>();
              if(nod.getNodeType() == Node.ELEMENT_NODE){
                  String content = (nodeList.item(i).getNodeName() + "  " + nod.getFirstChild().getNodeValue()).trim();
                  treeItem.setValue(content);
                  Item.getChildren().add(treeItem);
              }
              
              NodeList nodeList2 = nod.getChildNodes();
              addToTreeItem(treeItem, nodeList2);
              }
   		 }
	}
         
	
	
	




	