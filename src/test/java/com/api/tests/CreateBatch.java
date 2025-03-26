package com.api.tests;

import java.io.IOException;

import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.api.base.BaseClass;
import com.api.utils.ChainingData;

import com.api.utils.JsonReader;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateBatch extends BaseClass {

	BaseClass baseClass = new BaseClass();
	public static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
	Response response;
	RequestSpecification request;

	@Test(dataProvider = "jsondataProviderCreate")

	public void Createbatch(JSONObject batchData, String scenarioName, int expectedStatusCode) throws IOException {

		System.out.println("\nRunning Test for Scenario: " + scenarioName);

		// Replace the programId or any other dynamic data
		String jsondata = batchData.toJSONString();
		jsondata = jsondata.replace("{{programId}}", String.valueOf(ChainingData.programId));
		System.out.println("JSON Payload: " + jsondata);

		String endpoint = resourceBundle.getString("CreateBatch");
		response = baseClass.PostRequest(jsondata, endpoint);
		System.out.println("Response: " + response.asPrettyString());
		// Assert that the status code matches the expected value from JSON
		int actualStatusCode = response.getStatusCode();
		System.out.println("Actual Status Code: " + actualStatusCode);

		// Assertion
		assert actualStatusCode == expectedStatusCode
				: "Expected status code: " + expectedStatusCode + " but got: " + actualStatusCode;

		String batchId = response.jsonPath().getString("batchId");
		if (batchId != null && !batchId.isEmpty()) {
			System.out.println("Created Batch ID: " + batchId);
			ChainingData.batchId = Integer.parseInt(batchId);
		} else {
			System.err.println("Batch creation failed for scenario: " + batchData.get("batchName"));
		}
	}
}