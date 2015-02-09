package com.wstester.dispatcher.soap;

import javax.xml.namespace.QName;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;

import com.wstester.model.Server;
import com.wstester.model.SoapService;
import com.wstester.model.SoapStep;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class SoapConnection {

	@Autowired
	public CxfEndpoint endpoint;
	
	public void setConnection(SoapStep step) throws Exception{
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		
		Server server = projectFinder.getServerById(step.getServerId());
		SoapService service = (SoapService) step.getServiceId();
		
		endpoint.setAddress(server.getIp() + ":" + service.getPort() + service.getPath());
		endpoint.setWsdlURL(service.getWsdlURL());
		
		// TODO: set the portName and the serviceName dynamically by parsing the WSDL provided in service
		endpoint.setPortName(new QName("http://footballpool.dataaccess.eu", "InfoSoap"));
		endpoint.setServiceName(new QName("http://footballpool.dataaccess.eu", "Info"));
		
		endpoint.setDataFormat(DataFormat.RAW);
	}
}
