package com.wstester.asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import com.wstester.model.Asset;

public class AssetManagerTest {

	@Test
	public void addAsset() throws InterruptedException{
		
		AssetManager assetManager = new AssetManager();
		
		Asset asset = new Asset();
		asset.setName("AssetFile.xml");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);
		
		assetManager.waitUntilFileCopied(asset);
		
		File file = new File("assets/AssetFile.xml");
		assertTrue(file.exists());
		assetManager.close();
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
		assetManager.close();
		
		String content = assetManager.getAssetContent(asset);
		assertEquals("Harap Alb", content);
	}
	
	@After
	public void after(){
		
		File file = new File("assets/AssetFile.xml");
		file.delete();
	}
}
