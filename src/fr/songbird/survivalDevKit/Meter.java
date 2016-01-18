package fr.songbird.survivalDevKit;

import fr.songbird.survivalDevKit.exception.NullValueException;

/**
 * 
 * @author songbird
 * @version 0.0.1_0-ALPHA
 */
public class Meter {

	private int current_time;
	private int hour = 0,
		minutes = 0,
		seconds = 0;
	

	/**
	 * Le parametre du constructeur vaut: heureVoulue-1<br><br>
	 * 
	 * exemple:
	 * <code>
	 * 
	 *         //Je veux que l'on formate l'heure pendant 10 heures<br>
	 *         new Meter(9); //10-1
	 *         
	 * </code>
	 * @param hour
	 * @throws NullValueException
	 */
	public Meter(int hour) throws NullValueException
	{
		if(hour == 0)
		{
			new NullValueException("Le parametre entre vaut "+hour);
		}
		this.hour = hour;
		this.minutes = 59;
		this.seconds = 59;
		setCurrentTime(hour);
		
	}
	
	//###### PRIVATE METHODS ######
	
	private void setCurrentTime(int ct)
	{
		this.current_time = ct;
	}
	

	
	
	//###### PUBLIC METHODS ######
	
	public int getCurrentTime()
	{
		return current_time;
	}
	
	
	public int getHour()
	{
		return hour;
	}
	
	
	public int getMinutes()
	{
		return minutes;
	}
	
	public int getSeconds()
	{
		return seconds;
	}
	
	
	public String formatHour()
	{

			try
			{
				Thread.sleep(1000);
			}catch(InterruptedException ie0)
			{
				ie0.printStackTrace();
			}
			seconds--;
			if(seconds == 0 && minutes != 0)
			{
				minutes--;
				seconds = 59;
			}
			else if(minutes == 0 && hour != 0)
			{
				hour--;
				minutes = 59;
			}
			StringBuilder builder = new StringBuilder();
			builder.append(hour);
			builder.append("h ");
			builder.append(minutes);
			builder.append("min ");
			builder.append(seconds);
			builder.append("s");
		return builder.toString();
	}
	
	
}
