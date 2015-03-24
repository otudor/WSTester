package com.wstester.asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Asset;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IAssetManager;

public class AssetManagerTest extends TestBaseClass{

	@Test
	public void addAsset() throws Exception{
		
		IAssetManager assetManager = ServiceLocator.getInstance().lookup(IAssetManager.class);
		
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
	public void saveAsset() throws Exception{
		
		IAssetManager assetManager = ServiceLocator.getInstance().lookup(IAssetManager.class);
		
		Asset asset = new Asset();
		asset.setName("info.wsdl");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);
		
		assetManager.waitUntilFileCopied(asset);
		
		File file = new File("assets/info.wsdl");
		assertTrue(file.exists());
		
		assetManager.saveAsset(asset, "Testam daca este functional");
		
		String content = assetManager.getAssetContent(asset.getName());
		assertEquals("Testam daca este functional", content);
		
		file.delete();
	}
	
	@Test
	public void verifyContentAsset() throws Exception{
		
		IAssetManager assetManager = ServiceLocator.getInstance().lookup(IAssetManager.class);
		
		Asset asset = new Asset();
		asset.setName("AssetFile.txt");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);
		
		assetManager.waitUntilFileCopied(asset);
		
		File file = new File("assets/AssetFile.txt");
		assertTrue(file.exists());
		
		String content = assetManager.getAssetContent(asset.getName());
		assertEquals("Harap Alb", content);
		
		file.delete();
	}
}
