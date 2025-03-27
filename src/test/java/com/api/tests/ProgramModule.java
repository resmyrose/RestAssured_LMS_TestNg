package com.api.tests;

import java.io.IOException;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.base.BaseClass;
import com.api.model.responses.ProgramResponses;
import com.api.utils.ChainingData;
import com.api.utils.JsonReader;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ProgramModule extends BaseClass{

	JsonReader jsonreader = new JsonReader();
	BaseClass baseClass = new BaseClass();
	public static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
	Response response;
 
	 
	 
	
	@Test(dataProvider = "jsondataProviderCreate")
	public void createprogram(JSONObject programData, String scenarioName, int expectedStatusCode) throws IOException {

		System.out.println("\nRunning Test for Scenario: " + scenarioName);

		// Replace the programId or any other dynamic data
		String jsondata = programData.toJSONString();
		 
		 

		String endpoint = resourceBundle.getString("CreateProgram");
		response = baseClass.PostRequest(jsondata, endpoint);
		System.out.println("Response: " + response.asPrettyString());
		
		// Assert that the status code matches the expected value from JSON
		int actualStatusCode = response.getStatusCode();
		System.out.println("Actual Status Code: " + actualStatusCode);

		// Assertion
		assert actualStatusCode == expectedStatusCode
				: "Expected status code: " + expectedStatusCode + " but got: " + actualStatusCode;

		String programId = response.jsonPath().getString("programId");
		if (programId != null && !programId.isEmpty()) {
			System.out.println("Created Program ID: " + programId);
			ProgramResponses programresponse = response.as(ProgramResponses.class);
			ChainingData.programId = Integer.parseInt(programId);
			ChainingData.programName = programresponse.getProgramName();
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(resourceBundle.getString("ProgramSchema")));
			System.out.println("Schema Validation is Successfull for Create Program");
		} else {
			System.err.println("Program creation failed for scenario: " + programData.get("programName"));
		}
		
		
		
	}
	
	@Test
	public void getAllPrograms() {
		
		System.out.println("\nRunning Test for Scenario: GetAllPrograms" );
		response = baseClass.GetRequest(resourceBundle.getString("GetallPrograms"));
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	@Test
public void getProgrambyId() {
		
		System.out.println("\nRunning Test for Scenario: GetProgramById" );
		String endpoint = resourceBundle.getString("GetProgrambyId").replace("{programId}", String.valueOf(ChainingData.programId));
		System.out.println("Get by Program Id Endpoint "+endpoint);
		response = baseClass.GetRequest(endpoint);
		System.out.println("Response for GetbyProgramId "+ response.asPrettyString());
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	@Test(dataProvider = "jsondataProviderUpdate",dependsOnMethods = "createprogram")
	public void UpdateProgram(JSONObject programData, String scenarioName, int expectedStatusCode) {
		
		System.out.println("\nRunning Test for Scenario: " + scenarioName);

		// Replace the programId or any other dynamic data
		String jsondata = programData.toJSONString();
		 
		 

		String endpoint = resourceBundle.getString("UpdateProgramName").replace("{programName}", ChainingData.programName);
		response = baseClass.UpdateRequest(jsondata, endpoint);
		System.out.println("Response: " + response.asPrettyString());
		// Assert that the status code matches the expected value from JSON
		int actualStatusCode = response.getStatusCode();
		System.out.println("Actual Status Code: " + actualStatusCode);

		// Assertion
		assert actualStatusCode == expectedStatusCode
				: "Expected status code: " + expectedStatusCode + " but got: " + actualStatusCode;

		String programId = response.jsonPath().getString("programId");
		if (programId != null && !programId.isEmpty()) {
			System.out.println("Created Program ID: " + programId);
			ChainingData.programId = Integer.parseInt(programId);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(resourceBundle.getString("ProgramSchema")));
			System.out.println("Schema Validation is Successfull for Update Program");
			 
		} else {
			System.err.println("Program creation failed for scenario: " + programData.get("programName"));
		}
		
		
	}
	
//	@Test(dependsOnMethods = "UpdateProgram")
//	public void DeleteProgrambyId() {
//			
//			System.out.println("\nRunning Test for Scenario: Delete By ProgramById" );
//			String endpoint = resourceBundle.getString("DeleteProgramID").replace("{programId}", String.valueOf(ChainingData.programId));
//			response = baseClass.DeleteRequest(endpoint);
//			System.out.println("Response for GetbyProgramId "+ response.asPrettyString());
//			Assert.assertEquals(response.getStatusCode(), 200);
//			
//		}
	
	
	
}



    
