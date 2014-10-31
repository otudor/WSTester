package com.wstester.testFactory;

import java.io.IOException;
import java.io.StringReader;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlParser {
	
	public TreeView<Object> getTreeViewOfXml(String content) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		      
		 DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(content));

		    Document xmlDocument = db.parse(is);
		
        XPath xPath =  XPathFactory.newInstance().newXPath();
        
        String expression = ".";
        Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
        
        TreeItem<Object> treeItem = new TreeItem<>();
        TreeView<Object> treeView = new TreeView<>(treeItem);
        
        if(node != null) {
         	treeItem.setExpanded(true);
        	treeView.setShowRoot(false);
        	NodeList nodeList = node.getChildNodes();
            addToTreeItem(treeItem, nodeList);
        }
        
        return treeView;
	}
	
	void addToTreeItem(TreeItem<Object> Item, NodeList nodeList) {
		
		for (int i = 0; null != nodeList && i < nodeList.getLength(); i++) {
			Node nod = nodeList.item(i);
			TreeItem<Object> treeItem = new TreeItem<>();
			
			if (nod.getNodeType() == Node.ELEMENT_NODE) {
				if (nod.getFirstChild().getNodeValue() != null) {
					String content = (nodeList.item(i).getNodeName() + "  " + nod.getFirstChild().getNodeValue()).trim();
					treeItem.setValue(content);
					Item.getChildren().add(treeItem);
				} else {
					String content = (nodeList.item(i).getNodeName()).trim();
					treeItem.setValue(content);
					Item.getChildren().add(treeItem);
				}
			}

			NodeList nodeList2 = nod.getChildNodes();
			addToTreeItem(treeItem, nodeList2);
		}
	}
}
         
	
	
	




	