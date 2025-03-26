package com.api.base;

import java.util.ResourceBundle;
import java.util.stream.IntStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.api.utils.ChainingData;
import com.api.utils.JsonReader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseClass {
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    protected String jsonPath;
    protected String jsonKeyCreate;
    protected String jsonKeyUpdate;
    
    @BeforeClass
    @Parameters({"jsonPath", "jsonKeyCreate", "jsonKeyUpdate"})
    public void setUp(@Optional("") String jsonPath,@Optional("") String jsonKeyCreate,@Optional("") String jsonKeyUpdate) {
        this.jsonPath = jsonPath;
        this.jsonKeyCreate = jsonKeyCreate;
        this.jsonKeyUpdate = jsonKeyUpdate;
//        System.out.println("Reading JSON filename : " + jsonPath + " and key: " + jsonKey);
    }

    
     
/*This creates an IntStream of indices from 0 to batchData.size() - 1. 
 * The stream is used to iterate over each JSON object in the batchData array.*/
        
        @DataProvider(name = "jsondataProviderCreate")
        public Object[][] getCreateProgramData() {
            return getJsonData(jsonPath, jsonKeyCreate);
        }

        @DataProvider(name = "jsondataProviderUpdate")
        public Object[][] getUpdateProgramData() {
            return getJsonData(jsonPath, jsonKeyUpdate);
        }

        private Object[][] getJsonData(String jsonPath, String jsonKey) {
            JSONArray jsonData = JsonReader.getRequestBodies(jsonPath, jsonKey);
            return IntStream.range(0, jsonData.size())
                .mapToObj(i -> {
                    JSONObject jsonObject = (JSONObject) jsonData.get(i);
                    String scenarioName = (String) jsonObject.remove("scenarioName");
                    int statusCode = ((Long) jsonObject.remove("statusCode")).intValue();
                    return new Object[]{jsonObject, scenarioName != null ? scenarioName : "Unnamed Scenario", statusCode};
                })
                .toArray(Object[][]::new);
        }
    

   /* public Response Loginrequest(Object payload, String endpoint) {
        return RestAssured.given()
                .baseUri(resourceBundle.getString("baseuri"))  
                .body(payload)
                .when()
                .post(endpoint)  
                .then()
                .extract()
                .response();
    }*/
    

    public Response PostRequest(Object payload, String endpoint) {
        return RestAssured.given()
                .baseUri(resourceBundle.getString("baseuri")) // Ensure this key exists in config.properties
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ChainingData.token)
                .body(payload)
                .when()
                .post(endpoint)  
                .then()
                .extract()
                .response();
    }
    public Response UpdateRequest(Object payload, String endpoint) {
        return RestAssured.given()
                .baseUri(resourceBundle.getString("baseuri")) // Ensure this key exists in config.properties
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ChainingData.token)
                .body(payload)
                .when()
                .put(endpoint)  
                .then()
                .extract()
                .response();
    }
    
    public Response GetRequest(String endpoint) {
    	System.out.println("Endpoint "+endpoint);
        return RestAssured.given()
                .baseUri(resourceBundle.getString("baseuri")) // Ensure this key exists in config.properties
                .header("Authorization", "Bearer " + ChainingData.token)
                .when()
                .get(endpoint)  
                .then()
                 
                .extract()
                .response();
    }
    
    public Response DeleteRequest(String endpoint) {
    	System.out.println("Endpoint "+endpoint);
        return RestAssured.given()
                .baseUri(resourceBundle.getString("baseuri")) // Ensure this key exists in config.properties
                .header("Authorization", "Bearer " + ChainingData.token)
                .when()
                .delete(endpoint)  
                .then()
                .extract()
                .response();
    }
    
    

    
}
