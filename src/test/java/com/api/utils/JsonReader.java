package com.api.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

public class JsonReader {
	
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
	private static final String DATA_PATH = resourceBundle.getString("ProgramData");

	public static JSONObject getJsonData() {
	    JSONParser parser = new JSONParser();
	    try (FileReader reader = new FileReader(DATA_PATH)) {
	        return (JSONObject) parser.parse(reader);
	    } catch (IOException | ParseException e) {
	        throw new RuntimeException("Error reading/parsing JSON file: " + DATA_PATH, e);
	    }
	}
}

