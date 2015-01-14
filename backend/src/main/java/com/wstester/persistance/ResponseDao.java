package com.wstester.persistance;

import java.util.List;

import com.wstester.model.Response;

public interface ResponseDao {

	public Response getLastResponseForStepId(String stepId);
	public List<Response> getAllResponsesForStepId(String stepId);
	public void persistResponse(Response response);
}
