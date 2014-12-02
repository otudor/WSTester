package com.wstester.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import com.wstester.services.definition.IAssetManager;
import com.wstester.services.impl.AssetManager;

public class SoapRule extends Rule{

	private static final long serialVersionUID = 1L;
	@XmlType(name="soapInputType")
	public enum InputType {REQUEST};
	private InputType inputType;
	
	public SoapRule () {
	}
	
	public SoapRule(InputType inputType, String input, String output){
		
		this.inputType = inputType;
		this.inputString = input;
		this.output = output;
	}
	
	public SoapRule(InputType inputType, Asset input, String output){
		
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
		
		if(step instanceof SoapStep){
			
			if(inputType.equals(InputType.REQUEST)){
				
				if(inputString != null){
					return ((SoapStep)step).getRequest();
				}
				
				else if(inputAsset != null){
					
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
		return "SoapRule [inputType=" + inputType + ", inputString=" + inputString + ", inputAsset=" + inputAsset + ", output=" + output + "]";
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
		SoapRule other = (SoapRule) obj;
		if (inputType != other.inputType)
			return false;
		return true;
	}
}
