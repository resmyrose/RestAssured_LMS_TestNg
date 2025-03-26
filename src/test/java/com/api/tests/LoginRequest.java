package com.api.tests;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.testng.annotations.Test;

import com.api.base.BaseClass;
import com.api.model.responses.LoginResponse;
import com.api.utils.ChainingData;

import io.restassured.response.Response;
 
public class LoginRequest {
	
	
	
	BaseClass baseClass = new BaseClass();
	public static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
	
	HashMap<String,String> payload = new HashMap<>();
	Response response;
	
	@Test
	public void login() {
//		System.out.println(resourceBundle.getString("username"));
		payload.put("userLoginEmailId", resourceBundle.getString("username"));
		payload.put("password", resourceBundle.getString("password"));
		
		String endpoint = resourceBundle.getString("loginEndpoint");
		response = baseClass.PostRequest(payload,endpoint);
		
		 
		System.out.println(response.asPrettyString());
		
		LoginResponse loginresponse = response.as(LoginResponse.class);
		
		ChainingData.token = loginresponse.getToken();
		
	 
		
		
	}
	
	 
	
	
	

}