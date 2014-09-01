package com.wstester.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.wstester.model.MySQLService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;

public class MySQLRoute extends RouteBuilder  {

	private MySQLStep step = null;

	@Autowired
	private MysqlDataSource dataSource;
	
	@Override
	public void configure() throws Exception {

		from("jms:mySQLQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				step = exchange.getIn().getBody(MySQLStep.class);
				
				MySQLService service =(MySQLService) step.getService();
				int port = Integer.parseInt(service.getPort());
				
				dataSource.setServerName(step.getServer().getIp());
				dataSource.setPortNumber(port);
				dataSource.setDatabaseName(service.getDbName());
				dataSource.setUser(service.getUser());
				dataSource.setPassword(service.getPassword());
				
				exchange.getIn().setBody(step.getOperation());
			}
		})
		.recipientList(simple("jdbc:mySqlConnection"))
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				
				Message in = exchange.getIn();
				
				Response response = new Response();
				response.setStepID(step.getID());
				response.setContent(in.getBody(String.class));
				response.setPass(true);
				
				exchange.getIn().setBody(response);
			}
		})
		.to("jms:topic:responseTopic");
	}
}
