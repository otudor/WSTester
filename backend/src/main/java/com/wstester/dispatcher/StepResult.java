package com.wstester.dispatcher;

import java.util.ArrayList;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.wstester.model.Response;

@Component
public class StepResult implements ApplicationListener<ApplicationEvent>{

	private static ArrayList<Response> responseList = new ArrayList<Response>();
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		if(event instanceof StepRunEvent){
			responseList.add(((StepRunEvent) event).getResponse());
		}
	}
	
	public Response getResponse(String stepId){
		
		for(Response response : responseList){
			if(response.getStepID().equals(stepId)){
				responseList.remove(response);
				return response;
			}
		}
		
		return null;
	}
}
