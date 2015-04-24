package com.wstester.services.definition;

import java.util.List;

import com.wstester.model.Response;
import com.wstester.services.common.Stateless;

@Stateless
public interface ITestRunner extends IService{

	void run(Object testToRun);
	
	List<Response> getResponseList(String stepId, Long timeout);
}
