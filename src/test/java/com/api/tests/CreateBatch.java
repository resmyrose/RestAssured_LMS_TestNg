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

    // Static variables to hold jsonPath and jsonKey values from testng.xml
    private static String jsonPath;
    private static String jsonKey;

    // This method runs before the class is executed to fetch parameters from testng.xml
    @BeforeClass
    @Parameters({"jsonPath", "jsonKey"})
    public void setUp(String jsonPath, String jsonKey) {
        // Set the values from testng.xml to static variables
        CreateBatch.jsonPath = jsonPath;
        CreateBatch.jsonKey = jsonKey;
        System.out.println("Reading JSON file: " + jsonPath + " and key: " + jsonKey);
    }

    // DataProvider that reads data from the JSON file based on the static variables
    @DataProvider(name = "batchDataProvider")
    public Object[][] getBatchData() {
        // Fetch the JSON data for the Create Batch key
        JSONArray batchData = JsonReader.getRequestBodies(CreateBatch.jsonPath, CreateBatch.jsonKey);

        Object[][] testData = new Object[batchData.size()][3]; // Passing both jsonObject and scenarioName

        // Convert JSONArray to a 2D array of test data
        for (int i = 0; i < batchData.size(); i++) {
            JSONObject jsonObject = (JSONObject) batchData.get(i);

            // Extract scenario name for logging
            String scenarioName = jsonObject.get("scenarioName") != null ? jsonObject.get("scenarioName").toString() : "Unnamed Scenario";
            
            // Remove scenarioName before sending the request
            jsonObject.remove("scenarioName");
            int statusCode = ((Long) jsonObject.get("statusCode")).intValue();
            jsonObject.remove("statusCode");  // Remove statusCode from the request payload

            testData[i][0] = jsonObject; // Batch data
            testData[i][1] = scenarioName; // Scenario name for logging
            testData[i][2] = statusCode;
            // Store both the JSON object and scenario name
             
        }

        return testData;
    }


    @Test(dataProvider = "batchDataProvider")
    
    public void Createbatch(JSONObject batchData, String scenarioName,int expectedStatusCode) throws IOException {
        System.out.println("Test is running for scenario: " + scenarioName);  // Print scenario name

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
        assert actualStatusCode == expectedStatusCode : "Expected status code: " + expectedStatusCode + " but got: " + actualStatusCode;
        
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        String batchId = response.jsonPath().getString("batchId");
        if (batchId != null && !batchId.isEmpty()) {
            System.out.println("Created Batch ID: " + batchId);
        } else {
            System.err.println("Batch creation failed for scenario: " + batchData.get("batchName"));
        }
    }
}