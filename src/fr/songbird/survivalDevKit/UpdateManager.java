package fr.songbird.survivalDevKit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import fr.songbird.survivalDevKit.exception.InappropriateState;

/**
 * Systeme de mise a jour encore tres primitif, mais offrant la possibilite de gerer plus simplement ses mises a jour ainsi que la maniere de checker les majs.
 * @author songbird
 * @version 0.1.1_0-ALPHA [Major.Minor.VeryMinor_Bug-DevelopmentPhase]
 * @since 0.1.3_1-ALPHA
 */
public class UpdateManager {
	
	//###### PRIVATE VARIABLES ######
	
	private int version_currentProgram;
	private String[] repositories_resources;
	private String currentProgram_sum;
	private boolean checksum;
	private final File CLIENTPATHFOLDER_CLIENT = new File(getAppropriateUserHome(System.getProperty("os.name")));
	
	/**
	 * This constructor allows an update with a primitive system based on version number. 
	 * @param version_currentProgram
	 * @param repositories
	 * @see {@link UpdateManager#UpdateManager(String, String[], boolean)}
	 */
	public UpdateManager
	(
			int version_currentProgram, 
			String[] repositories
	)
	{
		this.version_currentProgram = version_currentProgram;
		this.repositories_resources = repositories;
	}
	
	/**
	 * If the boolean parameter ("checksum") isn't declared, user will use not overloaded constructor.
	 * State of checksum MUST BE to "true" for to use correctly this constructor.
	 * @param currentProgram_sum
	 * @param repositories
	 * @param CHECKSUM
	 * @see {@link UpdateManager#UpdateManager(String, String[], boolean)}
	 */
	public UpdateManager
	(
			String currentProgram_sum, 
			String[] repositories, 
			final boolean CHECKSUM
	)
	{
		this.currentProgram_sum = currentProgram_sum;
		this.repositories_resources = repositories;
		this.checksum = CHECKSUM;
		if(!this.checksum)
		{
			new InappropriateState(new String("Le parametre checksum vaut "+checksum));
			Runtime.getRuntime().exit(0x1);
		}
	}
	
	
	//###### PRIVATE METHODS ######
	/**
	 * Verifie simplement le numero de version pour appliquer (ou non) les mises a jour.
	 * @param version_mainProgram
	 * @return
	 */
	private boolean checkUpdate(int version_mainProgram)
	{
		if(version_currentProgram < version_mainProgram)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Verifie l'empreinte du logiciel pour effectuer (ou non) ensuite les mises a jour.
	 * @param mainProgram_sum
	 * @return true if current program version isn't equals to main program version, else false.
	 * @see {@link UpdateManager#checkUpdate(String)}
	 */
	private boolean checkUpdate(String mainProgram_sum)
	{
		if(!(currentProgram_sum.equals(mainProgram_sum)))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Appliquer les mises a jour.
	 * @param resource
	 * @param FINISHMESSAGE
	 */
	private void apply
	(
			String resource, 
			final String FINISHMESSAGE
	)
	{
		new Downloader(resource, new String(resource.substring(resource.lastIndexOf('/'))), repositories_resources);
		SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run() {
				JOptionPane.showConfirmDialog(null, FINISHMESSAGE);
				
			}
			
		});
	}
	
	/**
	 * Applique les mises a jour avec formatage dropbox.
	 * @param resource
	 * @param FINISHMESSAGE
	 * @param DROPBOX_DOWNLOAD
	 */
	private void apply
	(
			String resource, 
			final String FINISHMESSAGE, 
			final boolean DROPBOX_DOWNLOAD		
	)
	{
		new Downloader(resource, new String(resource.substring(resource.lastIndexOf('/'), resource.lastIndexOf("?"))), repositories_resources);
		SwingUtilities.invokeLater(
				new Runnable()
				{
		
					@Override
					public void run() {
						JOptionPane.showConfirmDialog(null, FINISHMESSAGE);
						
					}
					
				}
	    );
	}
	
	
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
	
	//###### PUBLIC METHODS ######
	
	/**
	 * cf. first UpdateManager constructor description.
	 * @param url_fileVersion URL du fichier contenant la version actuelle du logiciel.
	 * @param INFO_MESS Le contenu de la pop-up (le message que l'utilisateur est cense voir)
	 * @param TITLE_POPUP Le titre de la pop-up
	 * @param FINISHMESSAGE Message charge d'informer l'utilisateur que la tache est terminee du cote du systeme.
	 * @see {@link UpdateManager#update(String, String, String, String, boolean)}
	 */
	public void update
	(
			String url_fileVersion, 
			final String INFO_MESS, 
			final String TITLE_POPUP,
			final String FINISHMESSAGE
	)
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
	
	
	/**
	 * Constructeur surchargee qui integre le parametre permettant de telecharger et formater le nom d'un fichier obtenu par le biais d'un lien dropbox.
	 * @param DROPBOX_DL Ce parametre <strong> DOIT ETRE </strong> obligatoirement a l'etat "true" pour activer cette methode surchargee.
	 * @param url_fileVersion URL du fichier contenant la version actuelle du logiciel.
	 * @param INFO_MESS Le contenu de la pop-up (le message que l'utilisateur est cense voir)
	 * @param TITLE_POPUP Le titre de la pop-up
	 * @param FINISHMESSAGE Message charge d'informer l'utilisateur que la tache est terminee du cote du systeme.
	 */
	public void update
	(
			final boolean DROPBOX_DL,
			String url_fileVersion, 
			final String INFO_MESS, 
			final String TITLE_POPUP,
			final String FINISHMESSAGE
	)
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
				
				apply(currentLine, FINISHMESSAGE, true);
			}
			
			
		} catch (MalformedURLException mfu0) {
			
			mfu0.printStackTrace();
		} catch(IOException ioe0)
		{
			ioe0.printStackTrace();
		}
	}

	
	/**
	 * Works with md5 or sha1 (or another algorithm) sums.
	 * @param url_fileVersion URL du fichier contenant la version actuelle du logiciel.
	 * @param INFO_MESS Le contenu de la pop-up (le message que l'utilisateur est cense voir)
	 * @param TITLE_POPUP Le titre de la pop-up
	 * @param FINISHMESSAGE Message charge d'informer l'utilisateur que la tache est terminee du cote du systeme.
	 * @param CHECKSUM Variable booleenne permettant d'utiliser le constructeur supportant la verification d'une somme. Le variable <strong> doit obligatoirement </strong> avoir l'etat "true" pour cette methode surchargee.
	 * @see {@link UpdateManager#update(String, String, String, String)}
	 */
	public void update
	(
			String url_fileVersion, 
			final String INFO_MESS, 
			final String TITLE_POPUP,
			final String FINISHMESSAGE,
			final boolean CHECKSUM
	)
	{
		try {
			URL url = new URL(url_fileVersion);
			URLConnection urlc = url.openConnection();
			InputStream in = urlc.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
			String currentLine = null;
			while(!((currentLine = buffer.readLine()) == null))
			{

					if(checkUpdate(currentLine))
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
				
				
				apply(currentLine, FINISHMESSAGE);
			}
			
			
		} catch (MalformedURLException mfu0) {
			
			mfu0.printStackTrace();
		} catch(IOException ioe0)
		{
			ioe0.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param url_fileVersion URL du fichier contenant la version actuelle du logiciel.
	 * @param INFO_MESS Le contenu de la pop-up (le message que l'utilisateur est cense voir)
	 * @param TITLE_POPUP Le titre de la pop-up
	 * @param FINISHMESSAGE Message charge d'informer l'utilisateur que la tache est terminee du cote du systeme.
	 * @param CHECKSUM Variable booleenne permettant d'utiliser la methode  supportant la verification d'une somme. Le variable <strong> doit obligatoirement </strong> avoir l'etat "true" pour cette methode surchargee.
	 * @param DROPBOX_DL Variable booleenne permettant d'utiliser le formatage des noms de fichiers adaptes au telecharger par le biais de dropbox.
	 */
	public void update
	(
			String url_fileVersion, 
			final String INFO_MESS, 
			final String TITLE_POPUP,
			final String FINISHMESSAGE,
			final boolean CHECKSUM,
			final boolean DROPBOX_DL
	)
	{
		try {
			URL url = new URL(url_fileVersion);
			URLConnection urlc = url.openConnection();
			InputStream in = urlc.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
			String currentLine = null;
			while(!((currentLine = buffer.readLine()) == null))
			{

					if(checkUpdate(currentLine))
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
				
				
				apply(currentLine, FINISHMESSAGE, DROPBOX_DL);
			}
			
			
		} catch (MalformedURLException mfu0) {
			
			mfu0.printStackTrace();
		} catch(IOException ioe0)
		{
			ioe0.printStackTrace();
		}
	}
	

	
	
	
}
