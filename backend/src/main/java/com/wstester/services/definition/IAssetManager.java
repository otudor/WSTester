package com.wstester.services.definition;

import com.wstester.model.Asset;

/**
 * 
 * @author lvasile
 * since v 1.0
 * Backend Service responsible for managing Asset states: new/save/get/...
 */
public interface IAssetManager {

	void addAsset(Asset asset);
	
	void saveAsset(Asset asset);

	String getAssetContent(String fileName);

	void waitUntilFileCopied(Asset asset);

	void close();
}
