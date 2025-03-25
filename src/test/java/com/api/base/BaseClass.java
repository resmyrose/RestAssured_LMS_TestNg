package com.api.base;

import java.util.ResourceBundle;

import com.api.utils.ChainingData;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BaseClass {
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    public Response PostRequest(Object payload, String endpoint) {
         
        return RestAssured.given()
                .baseUri(resourceBundle.getString("baseuri")) // Ensure this key exists in config.properties
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ChainingData.token)
                .body(payload)
                .post(endpoint)
                .then()
                .extract()
                .response();
    }
}
