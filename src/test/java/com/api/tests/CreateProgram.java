package com.api.tests;

import java.io.IOException;

import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.api.base.BaseClass;
import com.api.model.responses.ProgramResponses;
import com.api.utils.ChainingData;
 
import com.api.utils.JsonReader;

import io.restassured.response.Response;

public class CreateProgram {

	JsonReader jsonreader = new JsonReader();
	BaseClass baseClass = new BaseClass();
	public static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
	Response response;
 
	 
	@Test
	@Parameters({"jsonPath", "jsonKey"})
     public void Createprogram(String jsonPath, String jsonKey) throws IOException {
        
		 
		JSONArray jsonDataArray = JsonReader.getRequestBodies(jsonPath, jsonKey);

	    for (Object obj : jsonDataArray) {
	        JSONObject jsonObject = (JSONObject) obj;
	    
        String endpoint = resourceBundle.getString("CreateProgram");
         
        // Make the API request with JSONObject as body
        Response response = baseClass.PostRequest(jsonObject, endpoint);  
        
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
         ChainingData.programName = programResponse.getProgramName();
       
	    }  
    }

}

    
