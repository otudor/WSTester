package com.wstester.asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Asset;
import com.wstester.services.impl.AssetManager;

public class AssetManagerTest extends TestBaseClass{

	@Test
	public void addAsset() throws InterruptedException{
		
		AssetManager assetManager = new AssetManager();
		
		Asset asset = new Asset();
		asset.setName("SOAPRequest.xml");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);
		
		assetManager.waitUntilFileCopied(asset);
		
		File file = new File("assets/SOAPRequest.xml");
		assertTrue(file.exists());
		
		file.delete();
	}
	
	@Test
	public void saveAsset() throws InterruptedException{
		
		AssetManager assetManager = new AssetManager();
		
		Asset asset = new Asset();
		asset.setName("SOAPRequest.xml");
		asset.setPath("src/test/resources");
		
		File file = new File("assets/SOAPRequest.xml");
		assertTrue(file.exists());
		
		try {
			assetManager.saveAsset(asset, "Testam daca este functional");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyContentAsset() throws Exception{
		
		AssetManager assetManager = new AssetManager();
		
		Asset asset = new Asset();
		asset.setName("AssetFile.xml");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);
		
		assetManager.waitUntilFileCopied(asset);
		
		File file = new File("assets/AssetFile.xml");
		assertTrue(file.exists());
		
		String content = assetManager.getAssetContent(asset.getName());
		assertEquals("Harap Alb", content);
		
		file.delete();
	}
}
