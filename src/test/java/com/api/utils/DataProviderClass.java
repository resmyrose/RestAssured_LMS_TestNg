package com.api.utils;

import org.testng.annotations.DataProvider;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderClass {

    @DataProvider(name = "jsonDataProvider")
    public Iterator<Object[]> provideJsonData() {
        List<Object[]> testCases = new ArrayList<>();
        
        // Read the entire JSON file
        JSONObject jsonData = JsonReader.getJsonData();
        
        if (jsonData == null) {
            throw new RuntimeException("JSON data could not be loaded!");
        }

        // Iterate over each key-value pair in the JSONObject
        for (Object key : jsonData.keySet()) {
            String testCaseName = key.toString();
            JSONObject testData = (JSONObject) jsonData.get(testCaseName);

            if (testData != null) {
                testCases.add(new Object[]{testCaseName, testData});
            } else {
                System.err.println("Warning: No data found for test case: " + testCaseName);
            }
        }

        return testCases.iterator();
    }
}
