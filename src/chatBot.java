/*
 * 
 * This is the chat bot (implementation of pircbot) that utilizes the
 * weatherCall class and the newsCall class to respond to the 
 * user with the corresponding information. The user can tell the program
 * to quit at any time.
 * 
 */


import java.io.IOException;
import java.text.*; 
import org.jibble.pircbot.*;

public class chatBot extends PircBot {
	
	//constructor
	public chatBot()
	{
		this.setName("nikhitakasana_chatbot");
	}
	
	//main
	public static void main(String[] args) throws Exception {
		
		//create new bot
		chatBot bot = new chatBot();
		bot.setVerbose(true);
		bot.connect("irc.freenode.net");
		bot.joinChannel("#pircbot"); 
		
		//send a message upon joining the channel
		bot.sendMessage("#pircbot", "Hello! I'm here to tell you about the weather in any city or give you the latest headline in the US. "
	   		+ "Ask for 'weather in [LOCATION]' or 'news' to get started.");
	}
	
	//to respond to user inputs
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		//if they ask about the weather
		if (message.contains("weather") || message.contains("Weather")) {
			
			//split to get the location
			String[] input = message.split(" ");
			String location = input[2];
			
			//if they entered a zip code
			if ( (location.charAt(0) > 47) && (location.charAt(0) < 58) ) {
				location = "zip=" + location;
			}
			
			//if they entered a city
			else {
				location = "q=" + location;
			}
			
			//set the location based on user input
			weatherCall.location = location;
			
			//call function
			weatherCall.main(null);
			
			//to put the temperature in a nice format
			DecimalFormat df = new DecimalFormat("#.##");
			String temp = df.format(weatherCall.parseTemp(weatherCall.result.toString()));
			
			//relay message to user
			sendMessage(channel, "The current temperature in " + input[2]  + " is " 
					+ temp + " degrees Fahrenheit.");
			sendMessage(channel, "Weather conditions can be described as " + weatherCall.description + ". ");
			
		} 
		
		//if the user says hi
		else if (message.contains("Hello") || message.contains("hello") || message.contains("Hey") || 
				message.contains("hey") || message.contains("Hi") || message.contains("hi")) 
		{
			//say hi back!
			sendMessage(channel, "Hi " + sender + "! ");
		}
		
		//if the user is arden
		else if (message.contains("Arden") || message.contains("arden"))
		{
			//say hi back!
			sendMessage(channel, "Hi Arden!");
			sendMessage(channel, "thinking about peeb");
			sendMessage(channel, "Love you!");
		}
		
		
		//if the user asks about the news
		else if (message.contains("News") || message.contains("news") || message.contains("Headlines") || 
				message.contains("headlines") || message.contains("Headline") || message.contains("headline")) 
		{
			//call the news api
			newsCall.main(null);
			
			//relay information to the user
			sendMessage(channel, "Here are the latest headlines: ");
			sendMessage(channel, "From " + newsCall.source1 + ": ");
			sendMessage(channel, newsCall.title1);
			sendMessage(channel, "From " + newsCall.source2 + ": ");
			sendMessage(channel, newsCall.title2);
			sendMessage(channel, "And from " + newsCall.source3 + ": ");
			sendMessage(channel, newsCall.title3);
		
		}
		
		//if the user is done
		else if (message.contains("exit") || message.contains("done") || message.contains("quit")) 
		{
			//say bye!
			sendMessage(channel, "See you later!");
			
			//end program
			disconnect();
			System.exit(0);
		}
		
		//if the user is polite and says thank you for relaying the information
		else if (message.contains("thanks") || message.contains("Thanks") || message.contains("Thank") || 
				message.contains("thank")) 
		{
			sendMessage(channel, "No problem!");
		}
		
		//if there's some invalid input
		else {
			sendMessage(channel, "I'm not sure what you want,  " + sender);
		}
		
	} //end of onMessage

}
