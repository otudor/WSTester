package com.wstester.explorer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Imageservice {
	
	private static Imageservice instance=null;
	private  ImageView dir = new ImageView(new Image(Imageservice.class.getResourceAsStream("computer.png")));
	private  ImageView fileUnkown = new ImageView(new Image(Imageservice.class.getResourceAsStream("blueprint.png")));
	protected Imageservice() {
		
	}
	
	
	   public static Imageservice getInstance() {
		      if(instance == null) {
		         instance = new Imageservice();
		      }
		      return instance;
		   }
	   
	   
	   
	   public ImageView getImageValue(PathItem p){
		   if (p.toString().toLowerCase().contains("my computer")) return new ImageView(new Image(Imageservice.class.getResourceAsStream("computer.png")));
		   if (p.getPath().getParent()==null) return new ImageView(new Image(Imageservice.class.getResourceAsStream("drive.png")));
		   if (p.isDirectory()) return new ImageView(new Image(Imageservice.class.getResourceAsStream("OpenDirectory.png")));
		   if (p.getExtension().equalsIgnoreCase("zip")) return new ImageView(new Image(Imageservice.class.getResourceAsStream("compress.png")));
		   if (p.getExtension().equalsIgnoreCase("csv")||p.getExtension().equalsIgnoreCase("xls")||p.getExtension().equalsIgnoreCase("xslx")) return new ImageView(new Image(Imageservice.class.getResourceAsStream("doc_excel_csv.png")));
		   if (p.getExtension().equalsIgnoreCase("txt")||p.getExtension().equalsIgnoreCase("doc")||p.getExtension().equalsIgnoreCase("docx")) return new ImageView(new Image(Imageservice.class.getResourceAsStream("text_signature.png")));
		   return new ImageView(new Image(Imageservice.class.getResourceAsStream("blueprint.png")));
	   }

}
