package fr.songbird.survivalDevKit;

import javax.swing.event.EventListenerList;

import fr.songbird.survivalDevKit.exception.NullValueException;
import fr.songbird.survivalDevKit.listeners.MeterListener;

/**
 * 
 * @author songbird
 * @version 0.2.1_1-BETA
 * @since TBM-3.5.1-1-BETA
 */
public class Meter {

	private int current_time;
	private long current_time_long;
	private int hour = 0,
		minutes = 0,
		seconds = 0;
	private EventListenerList list = new EventListenerList();
	

	public Meter()
	{
		
	}
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
	

	
	
	
	//###### PROTECTED METHODS ######
	
	protected void fireWhenTick()
	{
		
		for(MeterListener ml : getMeterListeners())
		{
			ml.whenTick(current_time);
			ml.whenTick(current_time_long);
		}
	}
	
	
	protected MeterListener[] getMeterListeners()
	{
		return list.getListeners(MeterListener.class);
	}

	
	
	//###### PUBLIC METHODS ######
	
	public void addMeterListener(MeterListener ml)
	{
		list.add(MeterListener.class, ml);
	}
	
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
			builder.append("h");
			builder.append(minutes);
			builder.append("m");
			builder.append(seconds);
			builder.append("s");
		return builder.toString();
	}
	
	public void countTick(int limit)
	{
		try
		{
			Thread.sleep(1000);
			setCurrentTime(1);
		}catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
		if(current_time < limit)
		{
			countTick(limit);
		}
	}
	
	public void countTick(long limit)
	{
		try
		{
			Thread.sleep(1000);
			setCurrentTime(1L);
		}catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
		if(current_time_long < limit)
		{
			countTick(limit);
		}
	}
	
	
	public void setCurrentTime(long ct)
	{
		this.current_time_long += ct;
		if(this.current_time_long > 5L)
		{
			this.current_time_long = 0;
		}
		fireWhenTick();
	}
	
	public void setCurrentTime(int ct)
	{
		this.current_time += ct;
		if(this.current_time > 5)
		{
			this.current_time = 0;
		}
		fireWhenTick();
	}
	
}
