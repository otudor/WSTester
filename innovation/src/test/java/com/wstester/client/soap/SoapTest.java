package com.wstester.client.soap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import com.cdyne.ws.weatherws.GetCityForecastByZIPResponse;
import com.wstester.server.Main;
import com.wstester.server.WeatherClient;

public class SoapTest {

	static ConfigurableApplicationContext ctx;

	@BeforeClass
	public static void setUp() throws Exception {
		ctx = Main.startSOAPServer();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		ctx.stop();
	}
	
	@Test
	public void test(){
		

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);

		String zipCode = "94304";

		GetCityForecastByZIPResponse response = weatherClient.getCityForecastByZip(zipCode);
		weatherClient.printResponse(response);
	}
}
