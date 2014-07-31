package com.wstester.dispatcher;

import com.wstester.client.Client;
import com.wstester.model.Step;

public interface Dispatcher {

	void dispatch(Step step, Client client);
}
