package com.wstester.asset;

import java.io.FileNotFoundException;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.PollingConsumer;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.log.CustomLogger;
import com.wstester.model.Asset;
import com.wstester.util.ProjectProperties;

//TODO: exception handler
public class AssetRoute extends RouteBuilder {
	
	private CustomLogger log = new CustomLogger(AssetRoute.class);
	
	@Override
	public void configure() throws Exception {
		
		from("jms:assetQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				Asset asset = exchange.getIn().getBody(Asset.class);
				
				exchange.getIn().setHeader("fileName", asset.getName());
				exchange.getIn().setHeader(Exchange.FILE_LAST_MODIFIED, asset.getLastmodified());
				
				//TODO: change the readLock option to readLockMarkerFile=false when upgrading to camel 2.14, currently using camel 2.13.2
				Endpoint fileEndpoint = exchange.getContext().getEndpoint("file:" + asset.getPath() + "?fileName=" + asset.getName() + "&noop=true&initialDelay=10&readLock=none");
				PollingConsumer consumer = fileEndpoint.createPollingConsumer();
				consumer.start();
				
				log.info(asset.getID(), "Copy asset content");
				ProjectProperties properties = new ProjectProperties();
				Long timeout = properties.getLongProperty("assetCopyTimeout");
				Exchange fileExchange = consumer.receive(timeout);
				if(fileExchange == null){
					throw new FileNotFoundException("File to be copied: " + asset.getPath() + "/" + asset.getName() + " is not found");
				}
				
				exchange.getIn().setBody(fileExchange.getIn().getBody());
				
				consumer.stop();
				fileExchange.handoverCompletions(exchange);
			}
		})
		.to("file:assets?fileName=${in.header.fileName}&autoCreate=true&keepLastModified=true&doneFileName=${file:name}.done");
	}
}
