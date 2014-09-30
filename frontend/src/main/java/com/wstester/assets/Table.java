package com.wstester.assets;

import com.wstester.model.Asset;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Table {
	
	private final StringProperty name;
	private final StringProperty date;
	private final StringProperty path;
	private final StringProperty size;
	private final StringProperty progress;
	private Asset asset;
	
	public Table(String name, String date, String path, String size, String progress){
		this.name = new SimpleStringProperty(name);
		this.date = new SimpleStringProperty(date);
		this.path = new SimpleStringProperty(path);
		this.size = new SimpleStringProperty(size);
		this.progress = new SimpleStringProperty(progress);
	}
	
	public String getName(){
		return name.get();
	}
	
	public String getDate(){
		return date.get();
	}
	
	public String getPath(){
		return path.get();
	}
	
	public String getSize(){
		return size.get();
	}
	
	public String getProgress(){
		return progress.get();
	}
	public StringProperty nameProperty(){
		return name;
	}
	
	public StringProperty dateProperty(){
		return date;
	}
	
	public StringProperty pathProperty(){
		return path;
	}
	
	public StringProperty sizeProperty(){
		return size;
	}
	public StringProperty progressProperty(){
		return progress;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
}
