package com.wstester.dispatcher.soap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.headers.Header;
import org.apache.cxf.headers.Header.Direction;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxb.JAXBDataBinding;

import com.wstester.authentication.SimpleAuthentication;
import com.wstester.model.SoapService;
import com.wstester.model.SoapStep;

public class SoapHeaderProcessor implements Processor {
	
	@Override
	public void process(Exchange exchange) throws Exception{
		
		List<SoapHeader> soapHeaders = CastUtils.cast((List<?>)exchange.getIn().getHeader(Header.HEADER_LIST));
        if (soapHeaders == null) {

        	soapHeaders = new ArrayList<SoapHeader>();
        }
        
        SoapStep step = exchange.getIn().getBody(SoapStep.class);
        
        SimpleAuthentication auth = new SimpleAuthentication();
        auth.setUsername(((SoapService)step.getService()).getUsername());
        auth.setPassword(((SoapService)step.getService()).getPassword());
        
	    try {
	    	SoapHeader header = new SoapHeader(new QName("http://xsoap.iccs.de/v1", "simpleAuth"),
	                auth, new JAXBDataBinding(SimpleAuthentication.class));
	        header.setDirection(Direction.DIRECTION_OUT);
	        header.setMustUnderstand(true);
	        soapHeaders.add(header);        
	        
	    } catch (JAXBException e) {
	    e.printStackTrace();
	    }
	}
}
