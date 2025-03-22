package com.api.base;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {
	
	public static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
	RequestSpecification requestSpecification;
	
	
	public BaseClass() {
		 requestSpecification =  given().baseUri(resourceBundle.getString("baseuri"));
		 
	}
	
	public Response loginRequest(Object payload)
	{
		return requestSpecification.contentType(ContentType.JSON).body(payload).post(resourceBundle.getString("loginEndpoint"));
	}
	
	
	  

}
