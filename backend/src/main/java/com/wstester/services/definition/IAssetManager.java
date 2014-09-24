package com.wstester.services.definition;

import java.io.IOException;

import com.wstester.model.Asset;

public interface IAssetManager extends IService {

	void addAsset(Asset asset);
	
	void saveAsset(Asset asset, String content);

	String getAssetContent(String fileName);

	boolean waitUntilFileCopied(Asset asset);
}
