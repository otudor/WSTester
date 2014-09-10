package com.wstester.asset;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.model.Step;
import com.wstester.services.impl.AssetManager;

public class AssetProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Step step = exchange.getProperty("step", Step.class);
		AssetManager assetManager = new AssetManager();
		
		if(step.getAssetList() != null){
			//TODO: now only the first asset will be set to the body of the request
			//TODO: in the future, send a request for every asset in the list
			exchange.getIn().setBody(assetManager.getAssetContent(step.getAssetList().get(0)));
		}
	}
}
