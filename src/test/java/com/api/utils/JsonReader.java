package com.api.utils;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private static String dataPath = new File(resourceBundle.getString("test.data.path")).getAbsolutePath() + File.separator;

    private static JSONParser parser = new JSONParser();

    @SuppressWarnings("unchecked")
	public static JSONArray getRequestBodies(String jsonFileName, String jsonKey) {
        System.out.println("Reading JSON file: " + jsonFileName);

        try {
            Object fileContent = parser.parse(new FileReader(dataPath + jsonFileName));
            JSONObject jsonObject = (JSONObject) fileContent;

            Object jsonData = jsonObject.get(jsonKey);

            if (jsonData == null) {
                throw new RuntimeException("NO DATA FOUND in JSON file '" + jsonFileName + "' for key '" + jsonKey + "'");
            }

            if (jsonData instanceof JSONArray) {
                return (JSONArray) jsonData; // Return directly if it's already an array
            } else if (jsonData instanceof JSONObject) {
                JSONArray singleEntryArray = new JSONArray();
                singleEntryArray.add((JSONObject) jsonData); // Explicitly cast
                return singleEntryArray;
            } else {
                throw new RuntimeException("Unexpected data format for key '" + jsonKey + "'");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("JSON file not found at path: " + dataPath + jsonFileName, e);
        } catch (IOException e) {
            throw new RuntimeException("IOException while reading file: " + jsonFileName, e);
        } catch (ParseException e) {
            throw new RuntimeException("Parse Exception occurred while parsing: " + jsonFileName, e);
        }
    }


}
