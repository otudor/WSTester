package com.wstester.dispatcher.rest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.model.RestService;
import com.wstester.model.RestStep;
import com.wstester.model.Server;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class RestPreProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		RestStep step = exchange.getIn().getBody(RestStep.class);
		exchange.setProperty("step", step);

		exchange.getIn().setBody(step.getRequest());
		exchange.getIn().setHeader(Exchange.HTTP_URI, getURI(step));
		exchange.getIn().setHeader(Exchange.HTTP_PATH, step.getPath());
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, step.getMethod());
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, step.getContentType());

		if (step.getQuery() != null) {
			for (String key : step.getQuery().keySet()) {
				exchange.getIn().setHeader(Exchange.HTTP_QUERY, key + "=" + step.getQuery().get(key));
			}
		}

		if (step.getHeader() != null) {
			for (String key : step.getHeader().keySet()) {
				exchange.getIn().setHeader(key, step.getHeader().get(key));
			}
		}

		if (step.getCookie() != null) {
			for (String key : step.getCookie().keySet()) {
				exchange.getIn().setHeader("Cookie", key + "=" + step.getCookie().get(key));
			}
		}
	}

	private Object getURI(RestStep step) throws Exception {

		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		
		Server server = projectFinder.getServerById(step.getServerId());
		RestService service = (RestService) projectFinder.getServiceById(step.getServiceId());

		return "http://" + server.getIp() + ":" + service.getPort();
	}
}
