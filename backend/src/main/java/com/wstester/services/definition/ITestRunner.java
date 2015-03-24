package com.wstester.services.definition;

import java.util.List;

import com.wstester.model.Response;
import com.wstester.services.common.Stateless;

@Stateless
public interface ITestRunner extends IService{

	void run(Object testToRun);
	
	@Deprecated
	Response getResponse(String stepId, Long timeout);
	List<Response> getDataProviderResponse(String stepId, Long timeout);
}
