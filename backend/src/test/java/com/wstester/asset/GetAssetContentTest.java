package com.wstester.asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.Test;

import com.wstester.model.Asset;

public class GetAssetContentTest {
	
	@Test
	public void addAsset() throws Exception{
		
		AssetManager assetManager = new AssetManager();
		
		Asset asset = new Asset();
		asset.setName("AssetFile.xml");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);
		
		Thread.sleep(2000);
		
		File file = new File("assets/AssetFile.xml");
		assertTrue(file.exists());
		assetManager.close();
		
		String content = assetManager.getAssetContent(asset);
		assertEquals("Merge ACUM", content);
	}
	
	@AfterClass
	public static void after(){
		
		File file = new File("assets/AssetFile.xml");
		file.delete();
	}
}
