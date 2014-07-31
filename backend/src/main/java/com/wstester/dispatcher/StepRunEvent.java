package com.wstester.dispatcher;

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
		// TODO Auto-generated constructor stub
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
