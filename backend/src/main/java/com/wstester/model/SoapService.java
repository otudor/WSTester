package com.wstester.model;

import java.util.UUID;

public class SoapService extends Service {

	private static final long serialVersionUID = 1L;
	
	private String Port;
	private String Path;
	
	public SoapService() {
		uuid = UUID.randomUUID().toString();
		setType(ServiceType.SOAP);
	}

	
	public String getPort() {
		return Port;
	}

	public String getPath() {
		return Path;
	}


	public void setPort(String port) {
		this.Port = port;
	}


	public void setPath(String path) {
		
		if(path.charAt(0)!='/') this.Path = '/'+path;
		else
			this.Path = path;
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SoapService other = (SoapService) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Port == null) {  //verificare port
			if (other.Port != null)
				return false;
		} else if(Path == null){ //verificare path
			if(other.Path != null)
				return false;
		}
		else if (!Port.equals(other.Port)) //verificare egalitate port-uri
			return false;
		else if (!Path.equals(other.Path)) //verificare egalitate path-uri
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
