package com.wstester.REST;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Json extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		String file = new String(Files.readAllBytes(Paths
				.get("C:/Users/gvasile/Desktop/json.txt")));

		Object json;
		try{
			json = new JSONObject(file);
		} catch(JSONException e){
			json = new JSONArray(file);
		}

		if (null != json) {

			TreeItem<Object> treeItem = new TreeItem<>();
			TreeView<Object> treeView = new TreeView<>(treeItem);
			treeItem.setExpanded(true);
			treeView.setShowRoot(false);
			addToTreeItem(treeItem, json);

			StackPane root = new StackPane();
			root.getChildren().add(treeView);
			Scene scene = new Scene(root, 300, 250);

			primaryStage.setTitle("Step1");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}

	public void addToTreeItem(TreeItem<Object> item, Object json) throws JSONException {
		
		if(json instanceof JSONObject){
			JSONObject jsonObject = (JSONObject)json;
			parseJsonObject(item, jsonObject);
			
		}else if(json instanceof JSONArray){
			JSONArray jsonArray = (JSONArray)json;
			parseJsonArray(item, jsonArray);
		}
	}

	private void parseJsonArray(TreeItem<Object> item, JSONArray jsonArray) throws JSONException {
		
		for (int i = 0; i < jsonArray.length(); i++) {
			TreeItem<Object> jsonArrayTreeItem = new TreeItem<>();
			jsonArrayTreeItem.setValue("[" + i + "]");
			item.getChildren().add(jsonArrayTreeItem);
			addToTreeItem(jsonArrayTreeItem, jsonArray.getJSONObject(i));
		}
		
	}

	@SuppressWarnings("unchecked")
	private void parseJsonObject(TreeItem<Object> item, JSONObject json) throws JSONException {
		
		Iterator<String> keyList = ((JSONObject) json).keys();
		
		while (keyList.hasNext()) {

			String key = keyList.next();

			if(json.get(key) instanceof JSONArray) {

				TreeItem<Object> treeItem = new TreeItem<>();
				treeItem.setValue(key);
				item.getChildren().add(treeItem);
				JSONArray array = (JSONArray) json.get(key);

				parseJsonArray(treeItem, array);
				
			}else if (json.get(key) instanceof JSONObject) {

				TreeItem<Object> treeItem = new TreeItem<>();
				treeItem.setValue(key);
				item.getChildren().add(treeItem);

				addToTreeItem(treeItem, json.getJSONObject(key));
				
			}else {

				TreeItem<Object> treeItem = new TreeItem<>();
				treeItem.setValue(key + ":  " + json.get(key));
				item.getChildren().add(treeItem);
			}
		}
	}
}
