package testing;

import java.util.List;

import javax.xml.namespace.QName;

import org.reficio.ws.SoapContext;
import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;



public class testSoapWs {

	public static void main(String[]args) {
		Wsdl wsdl = Wsdl.parse("http://www.webservicex.net/geoipservice.asmx?WSDL");
	//	System.out.println(wsdl.getBindings().get(0));
		List<QName> bindings = wsdl.getBindings();
		wsdl.printBindings();
		for ( QName q:bindings) {
			SoapBuilder builder = wsdl.binding().localPart(q.getLocalPart()).find();
			List<SoapOperation> operations = builder.getOperations();
			for (SoapOperation o: operations){
				System.out.println("OPERATION "+o.getOperationName() + "SOAPACTION "+ o.getSoapAction());
				if (!o.getSoapAction().isEmpty()) {
				//SoapOperation operation = builder.operation().name(o.getOperationName()).find();
				//System.out.println(operation.getOperationInputName());
				System.out.println(builder.buildInputMessage(o));
				System.out.println("\n");
				String expectedResponse = builder.buildOutputMessage(o, SoapContext.NO_CONTENT);
				System.out.println("EXPECTED \n");
				System.out.println(expectedResponse);
				}
			}
		}
		
		
		//System.out.println(wsdl.);
	}
	
}
