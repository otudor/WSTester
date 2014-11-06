package com.wstester.dispatcher.mysql;

import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;
import com.wstester.dispatcher.mongo.MongoExceptionRoute;

public class MysqlRoute extends MongoExceptionRoute {

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
