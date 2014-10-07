package com.wstester.asset;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.wstester.model.Asset;
import com.wstester.model.AssetType;
import com.wstester.model.Step;
import com.wstester.services.impl.AssetManager;

public class AssetProcessor implements Processor{

	private int boddyCount = 0;
	private HashMap<Asset, AssetType> assetMap;
	
	@Override
	public void process(Exchange exchange) {
		
		Step step = exchange.getProperty("step", Step.class);
		AssetManager assetManager = new AssetManager();
		assetMap = step.getAssetMap();
		
		if(assetMap != null){
			//TODO: now only the first asset will be set to the body of the request
			//TODO: in the future, send a request for every asset in the list
			
			for(Asset asset : assetMap.keySet()){
				
				if(assetMap.get(asset).equals(AssetType.BODY)){
					
					if(boddyCount==0){
						
						exchange.getIn().setBody(assetManager.getAssetContent(asset.getName()));
						boddyCount++;
					}
					else{
						
						//TODO: Throw an exception with an error message
					}
				}else
					 if(assetMap.get(asset).equals(AssetType.HEADER)){
						 
						//TODO: Must find out what should a header have
					 } 
			}
		}
	}
}
