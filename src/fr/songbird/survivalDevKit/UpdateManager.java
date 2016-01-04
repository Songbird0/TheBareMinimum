package fr.songbird.survivalDevKit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import fr.songbird.survivalDevKit.CheckInt;
import fr.songbird.survivalDevKit.Downloader;

/**
 * 
 * @author songbird
 * @version 0.1.1_0-ALPHA [Major.Minor.VeryMinor_Bug-DevelopmentPhase]
 * @since 0.1.3_1-ALPHA
 */
public class UpdateManager {
	
	//###### PRIVATE VARIABLES ######
	
	private int version_currentProgram;
	private String[] repositories_resources;
	private String currentProgram_sum;
	
	/**
	 * This constructor allows an update with a primitive system based on version number. 
	 * @param version_currentProgram
	 * @param repositories
	 * @see {@link UpdateManager#UpdateManager(String, String[], boolean)}
	 */
	public UpdateManager(int version_currentProgram, String[] repositories)
	{
		this.version_currentProgram = version_currentProgram;
		this.repositories_resources = repositories;
	}
	
	/**
	 * If the boolean parameter ("checksum") isn't declared, user will use not overloaded constructor.
	 * @param currentProgram_sum
	 * @param repositories
	 * @param checksum
	 * @see {@link UpdateManager#UpdateManager(int, String[])}
	 */
	public UpdateManager(String currentProgram_sum, String[] repositories, boolean checksum)
	{
		this.currentProgram_sum = currentProgram_sum;
		this.repositories_resources = repositories;
	}
	
	
	//###### PRIVATE METHODS ######
	
	private boolean checkUpdate(int version_mainProgram)
	{
		if(version_currentProgram < version_mainProgram)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param mainProgram_sum
	 * @return true if current program version isn't equals to main program version, else false.
	 * @see {@link UpdateManager#checkUpdate(int)}
	 */
	private boolean checkUpdate(String mainProgram_sum)
	{
		if(!(currentProgram_sum.equals(mainProgram_sum)))
		{
			return true;
		}
		return false;
	}
	
	
	private void apply(String resource, final String FINISHMESSAGE)
	{
		new Downloader(resource, new String(resource.substring(resource.lastIndexOf('/'), resource.lastIndexOf("?"))), repositories_resources);
		SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run() {
				JOptionPane.showConfirmDialog(null, FINISHMESSAGE);
				
			}
			
		});
	}
	
	//###### PUBLIC METHODS ######
	
	public void update(String url_fileVersion, 
			final String INFO_MESS, 
			final String TITLE_POPUP,
			final String FINISHMESSAGE)
	{
		try {
			URL url = new URL(url_fileVersion);
			URLConnection urlc = url.openConnection();
			InputStream in = urlc.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
			String currentLine = null;
			CheckInt check = new CheckInt();
			while(!(currentLine = buffer.readLine()).equals(null))
			{
				System.out.println(currentLine);
				if(check.isInt(currentLine))
				{

					if(checkUpdate(Integer.parseInt(currentLine)))
					{
						final int question = JOptionPane.showConfirmDialog(null, 
								INFO_MESS, 
								TITLE_POPUP, 
						        JOptionPane.YES_NO_OPTION
								);		
						if(question == JOptionPane.NO_OPTION || question == JOptionPane.CANCEL_OPTION)
						{
							break;
						}
					}
					else
					{
						break;
					}
				}
				
				apply(currentLine, FINISHMESSAGE);
			}
			
			
		} catch (MalformedURLException mfu0) {
			
			mfu0.printStackTrace();
		} catch(IOException ioe0)
		{
			ioe0.printStackTrace();
		}
	}
	
	
	
}
