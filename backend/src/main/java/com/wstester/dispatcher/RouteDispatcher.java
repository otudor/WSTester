package com.wstester.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import com.wstester.model.MongoStep;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;
import com.wstester.model.ServiceStatus;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;
import com.wstester.services.impl.StepManager;
import com.wstester.variable.VariableUsageProcessor;

public class RouteDispatcher extends GeneralExceptionRoute{
	
	@Override
	public void configure() throws Exception {
	    
		super.configure();
		
		from("jms:startQueue?concurrentConsumers=20&asyncConsumer=true")
		.bean(StepManager.class, "addStep")
		.bean(ExchangeDelayer.class, "delay")
		.process(new VariableUsageProcessor())
		.choice()
			.when(new Predicate() {				
				@Override
				public boolean matches(Exchange exchange) {
					return isMocked(exchange);
				}
			})
				.log("[${body.getId}] Sent message to mock queue")
				.to("jms:mockQueue")
			.when(body().isInstanceOf(RestStep.class))
				.log("[${body.getId}] Sent message to rest queue")
				.to("jms:restQueue")
			.when(body().isInstanceOf(MongoStep.class))
				.log("[${body.getId}] Sent message to mongo queue")
				.to("jms:mongoQueue")
			.when(body().isInstanceOf(MySQLStep.class))
				.log("[${body.getId}] Sent message to mysql queue")
				.to("jms:mySQLQueue")
			.when(body().isInstanceOf(SoapStep.class))
				.log("[${body.getId}] Sent message to soap queue")
				.to("jms:soapQueue")
		.endChoice();
	}

	protected boolean isMocked(Exchange exchange) {
		try {
			String serviceId = exchange.getIn().getBody(Step.class).getServiceId();
			IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
			ServiceStatus status = projectFinder.getServiceById(serviceId).getStatus();
			if(status != null && status.equals(ServiceStatus.MOCKED)){
				return true;
			}
			else {
				return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}