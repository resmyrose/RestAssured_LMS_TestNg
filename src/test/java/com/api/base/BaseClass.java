package com.api.base;

import java.util.ResourceBundle;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.api.utils.ChainingData;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseClass {
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    public static String jsonPath;
    public static String jsonKey;
    
//    @BeforeClass
//    @Parameters({"jsonPath", "jsonKey"})
//    public void setUp(String jsonPath, String jsonKey) {
//        BaseClass.jsonPath = jsonPath;
//        BaseClass.jsonKey = jsonKey;
//        System.out.println("Reading JSON file: " + jsonPath + " and key: " + jsonKey);
//    }
    
//    @BeforeClass
//    public void setUp() {
//        // Temporarily set them manually for testing
//        BaseClass.jsonPath = "/Users/amreennaziasyed/git/RestAssured_LMS_TestNg/src/test/resources/Data/BatchData.json";
//        BaseClass.jsonKey = "Create Batch";
//
//         
//    }



    public Response PostRequest(Object payload, String endpoint) {
        return RestAssured.given()
                .baseUri(resourceBundle.getString("baseuri")) // Ensure this key exists in config.properties
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ChainingData.token)
                .body(payload)
                .when()
                .post(endpoint) // Ensure endpoint is correctly mapped
                .then()
                .extract()
                .response();
    }

    
    

    
}
