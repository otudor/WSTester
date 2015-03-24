package com.wstester.services.definition;

import java.io.IOException;
import java.util.List;

import com.wstester.model.Asset;
import com.wstester.services.common.Stateless;

@Stateless
public interface IAssetManager extends IService {

	void addAsset(Asset asset);
	
	void saveAsset(Asset asset, String content);

	String getAssetContent(String fileName);
	List<String> getCSVrow(String fileName, int column) throws IOException;
	
	boolean waitUntilFileCopied(Asset asset);
}
