package com.wstester.asset;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.model.Asset;
import com.wstester.model.AssetType;
import com.wstester.model.Step;
import com.wstester.services.impl.AssetManager;

public class AssetProcessor implements Processor{

	private int bodyCount = 0;
	private Map<Asset, AssetType> assetMap;
	
	@Override
	public void process(Exchange exchange) {
		
		Step step = exchange.getProperty("step", Step.class);
		AssetManager assetManager = new AssetManager();
		assetMap = step.getAssetMap();
		
		if(assetMap != null){
			for(Asset asset : assetMap.keySet()){
				if(assetMap.get(asset).equals(AssetType.BODY)){
					
					if(bodyCount == 0){
						exchange.getIn().setBody(assetManager.getAssetContent(asset.getName()));
						bodyCount++;
					}
					else{
						//TODO: Throw an exception with an error message
					}
				}
				else if(assetMap.get(asset).equals(AssetType.HEADER)){
						//TODO: Must find out what should a header have
				} 
			}
		}
	}
}
