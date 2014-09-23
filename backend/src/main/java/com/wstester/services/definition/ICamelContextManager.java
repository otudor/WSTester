package com.wstester.services.definition;

import com.wstester.services.common.Stateful;

@Stateful
public interface ICamelContextManager extends IService{

	void startContext();
	
	void stopContext();
}
