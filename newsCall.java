/*
 * CS 2336.0U1 - Project 1 - API Calling/Pircbot
 * by Nikhita Kasana, net id nxk180001
 * 
 * This is the newsCall class, which will call the API from newsapi.
 * My API key is 4a580707b39a4b049f1ca26b125ebbb8
 * This calls the API and takes the first three news articles provided by the JSON
 * and gives the headlines to the user, as well as getting the url for the user
 * to click should they want to read more. It stores it in public variables so that
 * the chatBot can access it and relay the information to the user.
 * 
 * Due 7/28/2019
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

import java.io.InputStreamReader;
import java.io.BufferedReader; 

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

import java.io.IOException;

import java.net.URL;

public class newsCall {
	
	public static StringBuilder result;
	
	//the returned json
	public static String info;
	
	//the titles of the recent headlines
	public static String title1;
	public static String title2;
	public static String title3;
	
	//sources of the recent headlines, if user wants to read more
	public static String source1;
	public static String source2;
	public static String source3;

	public static void main(String[] args) {
		
		try {
			
			//api url
			String newsURL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=4a580707b39a4b049f1ca26b125ebbb8";
			
			//the string builder to hold all the json
			result = new StringBuilder();
			
			//make the url
			URL url = new URL(newsURL);
			
			//make connection, get data
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			//to read in data
			String line;
			
	    	//read the output
	    	while ((line = rd.readLine()) != null) {
	    		result.append(line);
	    	}
	    	rd.close();
	    	
	    	//make json into string to parse
	    	info = result.toString();
	    	
	    	//run the function to get the headlines
	    	parseNews(info);
		
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//get titles
	static String parseNews(String json) {
		
		JsonElement jelement = new JsonParser().parse(json);
    	JsonObject MasterNewsObject = jelement.getAsJsonObject();

    	//to get the articles
        JsonArray arr = MasterNewsObject.getAsJsonArray("articles");
        
        //get first title
        title1 = arr.get(0).getAsJsonObject().get("title").toString();
        
        //get second title
        title2 = arr.get(1).getAsJsonObject().get("title").toString();
        
        //get third title
        title3 = arr.get(2).getAsJsonObject().get("title").toString();
        
        //get first source
        source1 = arr.get(0).getAsJsonObject().get("url").getAsString();
        
        //get second source
        source2 = arr.get(1).getAsJsonObject().get("url").getAsString();
        
        //get third source
        source3 = arr.get(2).getAsJsonObject().get("url").getAsString();
        
        return title1;
		
    }
	
	

}
