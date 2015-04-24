package com.wstester.persistance;

import java.util.Date;
import java.util.List;

import com.wstester.model.Response;

public interface ResponseDao {

	public List<Response> getLastResponseListForStepId(String stepId, Date runDate);
	public List<Response> getAllResponsesForStepId(String stepId);
	public void persistResponse(Response response);
	public int getNumberOfReceivedResponses(String stepId, Date runDate);
}
