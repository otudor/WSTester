package com.wstester.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import com.wstester.services.definition.IAssetManager;
import com.wstester.services.impl.AssetManager;

public class RestRule extends Rule{

	private static final long serialVersionUID = 1L;
	@XmlType(name="restInputType")
	public enum InputType {PATH, METHOD, BODY};
	private InputType inputType;
	
	public RestRule(){
	}
	
	public RestRule(InputType inputType, String input, String output){
		
		this.inputType = inputType;
		this.inputString = input;
		this.output = output;
	}
			
	public RestRule(InputType inputType, Asset input, String output){
		
		this.inputType = inputType;
		this.inputAsset = input;
		this.output = output;
	}
	
	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	@Override
	protected Object getStepInput(Step step) {
		IAssetManager assetManager = new AssetManager();
		
		if(step instanceof RestStep){
			
			if(inputType.equals(InputType.PATH)){
				return ((RestStep) step).getPath();
			}
			
			if(inputType.equals(InputType.METHOD)){
				return ((RestStep) step).getMethod().toString();
			}
			
			if(inputType.equals(InputType.BODY)){
				
				
				if(((RestStep) step).getRequest() != null){
					return ((RestStep) step).getRequest();
				}
				else if(step.getAssetMap() != null){
					Map<Asset, AssetType> assetMap = step.getAssetMap();
					
					for(Asset asset : assetMap.keySet()){
						if(assetMap.get(asset).equals(AssetType.BODY)){
							return assetManager.getAssetContent(asset.getName());
						}
					}
				}
			}
		}
		return null;
		
	}

	@Override
	public String toString() {
		return "RestRule [inputType=" + inputType + ", inputString=" + inputString + ", inputAsset=" + inputAsset + ", output=" + output + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((inputType == null) ? 0 : inputType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestRule other = (RestRule) obj;
		if (inputType != other.inputType)
			return false;
		return true;
	}
}
