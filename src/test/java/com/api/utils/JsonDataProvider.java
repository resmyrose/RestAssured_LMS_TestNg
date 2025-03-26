//package com.api.utils;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.testng.annotations.DataProvider;
//
//import com.api.base.BaseClass;
//import com.api.tests.CreateBatch;
//
//public class JsonDataProvider {
//
//    public static Object[][] getTestData(String jsonPath, String jsonKey) {
//        JSONArray data = JsonReader.getRequestBodies(jsonPath, jsonKey);
//        System.out.println("Data retrieved from JSON: " + data);
//
//        Object[][] testData = new Object[data.size()][3];
//
//        for (int i = 0; i < data.size(); i++) {
//            JSONObject jsonObject = (JSONObject) data.get(i);
//
//            String scenarioName = jsonObject.get("scenarioName") != null ? jsonObject.get("scenarioName").toString() : "Unnamed Scenario";
//            jsonObject.remove("scenarioName");
//
//            int statusCode = ((Long) jsonObject.get("statusCode")).intValue();
//            jsonObject.remove("statusCode");
//
//            testData[i][0] = jsonObject;
//            testData[i][1] = scenarioName;
//            testData[i][2] = statusCode;
//        }
//
//        return testData;
//    }
//
//    @DataProvider(name = "DataProvider")
//    public Object[][] DataProvider() {
//          // Debug log
//        Object[][] testData = JsonDataProvider.getTestData(BaseClass.jsonPath, BaseClass.jsonKey);
//        System.out.println("Batch Data Size: " + testData.length);  // Check size of data returned
//        return testData;
//    }
//
//
//}
