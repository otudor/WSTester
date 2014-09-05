package com.wstester.asset;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.Test;

import com.wstester.model.Asset;

public class AddAssetTest {

	@Test
	public void addAsset() throws InterruptedException{
		
		AssetManager assetManager = new AssetManager();
		
		Asset asset = new Asset();
		asset.setName("AssetFile.xml");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);
		
		Thread.sleep(2000);
		
		File file = new File("assets/AssetFile.xml");
		assertTrue(file.exists());
		assetManager.close();
	}
	
	@AfterClass
	public static void after(){
		
		File file = new File("assets/AssetFile.xml");
		file.delete();
	}
}
