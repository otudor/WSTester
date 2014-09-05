package com.wstester.asset;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.PollingConsumer;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.model.Asset;

public class AssetRoute extends RouteBuilder {

	Asset asset;
	
	@Override
	public void configure() throws Exception {
		
		from("jms:assetQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				asset = exchange.getIn().getBody(Asset.class);
				exchange.getIn().setHeader("fileName", asset.getName());
				exchange.getIn().setHeader(Exchange.FILE_LAST_MODIFIED, asset.getLastmodified());
				
				//TODO: change the readLock option to readLockMarkerFile=false when upgrading to camel 2.14, currently using camel 2.13.2
				Endpoint fileEndpoint = exchange.getContext().getEndpoint("file:" + asset.getPath() + "?fileName=" + asset.getName() + "&noop=true&initialDelay=10&readLock=none");
				PollingConsumer consumer = fileEndpoint.createPollingConsumer();
				consumer.start();
				Exchange fileExchange = consumer.receiveNoWait();
				
				exchange.getIn().setBody(fileExchange.getIn().getBody(String.class));
				
				consumer.stop();
				fileExchange.handoverCompletions(exchange);
			}
		})
		.to("file:assets?fileName=${in.header.fileName}&autoCreate=true&keepLastModified=true");
	}

}
