package fr.songbird.survivalDevKit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UMServer {
	
	//###### PRIVATE VARIABLES ######
	
	private final File CLIENTPATHFOLDER_SERVER = new File(
			
			getAppropriateUserHome(System.getProperty("os.name"))
			);
	
	
	@SuppressWarnings("unchecked")
	public UMServer()
	{
		
	}
	
	
	//###### PRIVATE METHODS ######
	
	private String getAppropriateUserHome(final String OS)
	{
		final String OsUpperCase = (OS == null ? "nop" : OS.toUpperCase());
		
		if(!OsUpperCase.equals("nop"))
		{
			if(OsUpperCase.contains("WIN"))
			{
				return System.getProperty("user.home")+File.separator+"AppData"+File.separator+"Roaming"+File.separator;
				
			}
			else if(OsUpperCase.contains("NUX"))
			{
				return System.getProperty("user.home")+File.separator;
			}
			else
			{
				return System.getProperty("user.home")+File.separator;
			}
		}
		
		return "Your operating system are not found";
	}


}
