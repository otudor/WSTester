package com.wstester.events;

import org.springframework.context.ApplicationEvent;
import com.wstester.model.Response;

public class StepRunEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3210665332315319888L;

	private Response response;
	
	public StepRunEvent(Object source) {
		super(source);
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
