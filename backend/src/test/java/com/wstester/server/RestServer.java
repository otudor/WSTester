package com.wstester.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("customer")
public class RestServer {

	static Customer customer;
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("getCustomers")
	public String getCustomers() {

		return "All customers";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("searchCustomer")
	public String searchCustomerByName(@QueryParam("name") String name) {

		return name;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("searchWithCookie")
	public String searchCustomerWithCookie(@CookieParam("name") String name){
		
		return name;
	}
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("searchWithHeader")
	public String searchCustomerWithHeader(@HeaderParam("name") String name){
		
		return name;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("searchWithAll")
	public String searchCustomerAll(@QueryParam("queryName") String queryName, @CookieParam("cookieName") String cookieName, @HeaderParam("headerName") String headerName){
		
		return queryName + "," + cookieName + "," + headerName;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("searchByName/name/{name}")
	public String searchByName(@PathParam("name") String name){
		
		return name;
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("insertCustomer")
	public String insertCustomer(String name){
		
		return name;
	}
	
	@POST
	@Path("insertCustomerByJson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer insertCustomerByJSON(Customer customer){
		
		return new Customer(customer.getName());
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("addCustomer")
	public String insertCustomerReturnId(String name){
		
		customer = new Customer(name);
		return customer.getId();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("searchById/id/{id}")
	public String searchById(@PathParam("id") String id){
		
		if (customer.getId().equals(id)) {
			return customer.getName();
		} 
		return "Customer with id: " + id + "was not found";
	}
}
