package com.wstester.dispatcher.mysql;

import org.apache.camel.builder.RouteBuilder;
import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;

public class MysqlRoute extends RouteBuilder  {

	@Override
	public void configure() throws Exception {

		from("jms:mySQLQueue?concurrentConsumers=20&asyncConsumer=true")
		
		.bean(ExchangeDelayer.class, "delay")
		.bean(MysqlConnection.class)
		.process(new MysqlPreProcessor())
		.process(new AssetProcessor())
		
		.recipientList(simple("jdbc:mySqlConnection"))
		
		.process(new MysqlPostProcessor())
		.to("jms:topic:responseTopic");
	}
}
