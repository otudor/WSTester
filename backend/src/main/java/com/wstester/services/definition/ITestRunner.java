package com.wstester.services.definition;

import com.wstester.model.Response;
import com.wstester.services.common.Stateless;

@Stateless
public interface ITestRunner extends IService{

	void run(Object testToRun);
	
	Response getResponse(String stepId, Long timeout);
}
