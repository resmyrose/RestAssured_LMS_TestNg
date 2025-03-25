package com.api.tests;

import java.io.IOException;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.base.BaseClass;
import com.api.model.responses.ProgramResponses;
import com.api.utils.ChainingData;
import com.api.utils.DataProviderClass;

import io.restassured.response.Response;

public class CreateProgram {

	BaseClass baseClass = new BaseClass();
	public static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
	Response response;

	
	 
	@Test( dataProvider = "jsonDataProvider", dataProviderClass = DataProviderClass.class)
     public void Createprogram(String jsonKey, JSONObject data) throws IOException {
        // Print details of the test case
		System.out.println(ChainingData.token);
        String jsonString = data.toJSONString();
        System.out.println("Executing Test Case: " + jsonKey);
        System.out.println("JSON Payload: " + jsonString);
        String endpoint = resourceBundle.getString("CreateProgram");
         
        // Make the API request with JSONObject as body
        Response response = baseClass.PostRequest(jsonString, endpoint); // Pass JSONObject directly
        
        // Print response details
        System.out.println("Response: " + response.asPrettyString());

        // Extract programId from response and validate
        String programId =  response.jsonPath().getString("programId");
        if (programId != null && !programId.isEmpty()) {
            System.out.println("Created Program ID: " + programId);
        } else {
            System.err.println("Program creation failed for test case: " + jsonKey);
        }
        ProgramResponses programResponse = response.as(ProgramResponses.class);
         ChainingData.programId =  programResponse.getProgramId();
         System.out.println(ChainingData.programId);
         
    }

}

    
