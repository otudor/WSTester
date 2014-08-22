package com.wstester.dispatcher;

import java.util.ArrayList;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import com.wstester.events.StepRunEvent;
import com.wstester.model.Response;

public class ResponseCallback implements ApplicationListener<ApplicationEvent>{

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
