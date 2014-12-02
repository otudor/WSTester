package com.wstester.services.definition;

import com.wstester.model.Asset;
import com.wstester.services.common.Stateless;

@Stateless
public interface IAssetManager extends IService {

	void addAsset(Asset asset);
	
	void saveAsset(Asset asset, String content);

	String getAssetContent(String fileName);

	boolean waitUntilFileCopied(Asset asset);
}
