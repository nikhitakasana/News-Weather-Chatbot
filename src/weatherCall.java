/*
 * 
 * This is the weatherCall class, which will call the API from 
 *
 * This calls the API based on the user's input, parses through the JSON to get
 * relevant information and stores it for the chatBot to access and relay
 * the information to the user.
 *
 */


import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

public class weatherCall {
	
	public static String city;
	public static String location;
	public static String description;
	public static StringBuilder result;
	public static String info;
	public static Double temp;
	public static double t;

	public static void main(String[] args) {
		
		try {
			
			//pieces of url
			String myAPIurl = "http://api.openweathermap.org/data/2.5/weather?";
			String myAPIToken = "&APPID=4ac103f38364a3a932007ad244a16422";
	
			//location will hold the right specifiers based on if the user inputted a city name or zip code
			String weatherURL = myAPIurl + location + myAPIToken; 
			
			//make string builder to read in json
			result = new StringBuilder();
			URL url = new URL(weatherURL);
			
			//establish connection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			//to read in input
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			
	    	//reads the output
	    	while ((line = rd.readLine()) != null) {
	    		result.append(line);
	    	}
	    	rd.close();
	    	
	    	//makes string builder into string
	    	info = result.toString();
	    	
	    	//get the weather's description
	    	parseWeather(info);
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//to get the temperature, in fahrenheit
	static Double parseTemp (String json) {
		
    	JsonElement jelement = new JsonParser().parse(json);
    	JsonObject weatherReport = jelement.getAsJsonObject();
    	
    	JsonObject mainObject = weatherReport.getAsJsonObject("main");
    	
    	//get current temperature
    	t = mainObject.get("temp").getAsDouble();	
    	
    	//convert to fahrenheit
    	t = t * 9/5 - 459.67;

    	//wrap into BIG Double class so that chatBot can make it toString
    	temp = new Double(t);
    	return temp;
    }
	
	//get the weather description, i.e. "cloudy" or "sunny"
	static String parseWeather(String json) {
		
		JsonElement jelement = new JsonParser().parse(json);
    	JsonObject weatherReport = jelement.getAsJsonObject();

    	//to get the weather array
        JsonArray arr = weatherReport.getAsJsonArray("weather");
        
        //get description
        description = arr.get(0).getAsJsonObject().get("description").toString();
        
        return description;
	}

    
}
