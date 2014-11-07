package com.wstester.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Parser {

	public TreeView<Object> parseXML(String content) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {

		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(content));

		Document xmlDocument = db.parse(is);

		XPath xPath = XPathFactory.newInstance().newXPath();

		String expression = ".";
		Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);

		TreeItem<Object> treeItem = new TreeItem<>();
		TreeView<Object> treeView = new TreeView<>(treeItem);

		if (node != null) {
			treeItem.setExpanded(true);
			treeView.setShowRoot(false);
			NodeList nodeList = node.getChildNodes();
			addToXMLTreeItem(treeItem, nodeList);
		}

		return treeView;
	}

	private void addToXMLTreeItem(TreeItem<Object> Item, NodeList nodeList) {

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
			addToXMLTreeItem(treeItem, nodeList2);
		}
	}

	public TreeView<Object> parseJSON(String content) throws JSONException {

		Object json;
		try {
			json = new JSONObject(content);
		} catch (JSONException e) {
			json = new JSONArray(content);
		}

		TreeItem<Object> treeItem = new TreeItem<>();
		TreeView<Object> treeView = new TreeView<>(treeItem);
		treeItem.setExpanded(true);
		treeView.setShowRoot(false);
		addToJSONTreeItem(treeItem, json);
			
		return treeView;

	}

	private void addToJSONTreeItem(TreeItem<Object> item, Object json) throws JSONException {

		if (json instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) json;
			parseJsonObject(item, jsonObject);

		} else if (json instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) json;
			parseJsonArray(item, jsonArray);
		}
	}

	private void parseJsonArray(TreeItem<Object> item, JSONArray jsonArray) throws JSONException {

		for (int i = 0; i < jsonArray.length(); i++) {
			TreeItem<Object> jsonArrayTreeItem = new TreeItem<>();
			jsonArrayTreeItem.setValue("[" + i + "]");
			item.getChildren().add(jsonArrayTreeItem);
			addToJSONTreeItem(jsonArrayTreeItem, jsonArray.getJSONObject(i));
		}
	}

	@SuppressWarnings("unchecked")
	private void parseJsonObject(TreeItem<Object> item, JSONObject json) throws JSONException {

		Iterator<String> keyList = ((JSONObject) json).keys();

		while (keyList.hasNext()) {

			String key = keyList.next();

			if (json.get(key) instanceof JSONArray) {

				TreeItem<Object> treeItem = new TreeItem<>();
				treeItem.setValue(key);
				item.getChildren().add(treeItem);
				JSONArray array = (JSONArray) json.get(key);

				parseJsonArray(treeItem, array);

			} else if (json.get(key) instanceof JSONObject) {

				TreeItem<Object> treeItem = new TreeItem<>();
				treeItem.setValue(key);
				item.getChildren().add(treeItem);

				addToJSONTreeItem(treeItem, json.getJSONObject(key));

			} else {

				TreeItem<Object> treeItem = new TreeItem<>();
				treeItem.setValue(key + ":  " + json.get(key));
				item.getChildren().add(treeItem);
			}
		}
	}
}
