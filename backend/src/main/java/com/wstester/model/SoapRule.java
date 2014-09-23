package com.wstester.model;

import com.wstester.services.definition.IAssetManager;
import com.wstester.services.impl.AssetManager;

public class SoapRule extends Rule{

	private static final long serialVersionUID = 1L;
	
	public enum InputType {REQUEST};
	private InputType inputType;
	
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
	
	@Override
	public String run(Step step) {

		if(step instanceof SoapStep){
			
			if(inputType.equals(InputType.REQUEST)){
				if((inputString != null) && ((SoapStep)step).getRequest().equals(inputString)){
					return output;
				}
				
				//TODO: now only the first asset will be set to the body of the request
				//TODO: in the future, send a request for every asset in the list
				IAssetManager assetManager = new AssetManager();
				if((inputAsset != null) && step.getAssetList() != null && step.getAssetList().get(0) !=null &&
						(step.getAssetList().get(0).getName().equals(inputAsset.getName()) ||
						(assetManager.getAssetContent(step.getAssetList().get(0).getName()).equals(assetManager.getAssetContent(inputAsset.getName())))))		
					return output;
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
