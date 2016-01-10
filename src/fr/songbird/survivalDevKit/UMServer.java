package fr.songbird.survivalDevKit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Version serveur du systeme de mise a jour:
 * Elle est chargee de fournir un fichier au format json compose de tous les chemins relatifs (jusqu'au dossier .[DossierClient])
 * et generer une somme md5 pour tout le dossier client et empecher toute modification (ou permettre verification pour une potentielle mise a jour)
 * @author songbird
 * @version 0.0.1_0-ALPHA
 * @see {@link UpdateManager#UpdateManager(int, String[])}
 */
public class UMServer {
	
	//###### PRIVATE VARIABLES ######
	

	/**
	 * Chemin du dossier client cense etre conserve sur le serveur
	 */
	private  File CLIENTPATHFOLDER_SERVER;
	
	@SuppressWarnings("unchecked")
	public UMServer()
	{
		
		
		
	}
	
	
	//###### PRIVATE METHODS ######
	
	/**
	 * 
	 * @param OS
	 * @return le chemin adequat en fonction du systeme d'exploitation
	 */
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
		}
		
		return System.getProperty("user.home")+File.separator;
	}
	
	/**
	 * Permet de creer un chemin relatif pour eviter de casser la portabilite lors du telechargement vers le client.
	 * Exemple: <br>
	 * <code>
	 * 
	 * /home/songbird/.minecraft/unDossier/unfichier<br>
	 * getRelativepath(/home/songbird/.minecraft/unDossier/unfichier, .minecraft);<br>
	 * 
	 * Renvoie == .minecraft/unDossier/unFichier
	 * 
	 * </code>
	 * @param target_path
	 * @param target_file
	 * @return le chemin relatif voulu si le dossier/fichier cible existe bien dans le chemin, sinon renvoie null.
	 */
	private String getRelativePath(String target_path, String target_file)
	{
		CheckEntry checker = new CheckEntry();
		
		if(checker.heExists(target_path, target_file))
		{
			return target_path.substring(target_path.indexOf(target_file));
		}
		
		
		return null;
	}
	
	
	//###### PUBLIC METHODS ######
	
	
	public void setClientPathFolder(final String CLIENT_FOLDER)
	{
		this.CLIENTPATHFOLDER_SERVER = new File(
				
				getAppropriateUserHome(System.getProperty("os.name"))+File.separator+CLIENT_FOLDER
				);
	}
	
	
	public String getMD5Sum(final File target_file) throws NoSuchAlgorithmException, IOException
	{
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			FileInputStream fileInput = new FileInputStream(target_file);
			
			byte[] buffer = new byte[1024];
			
			int reading = 0;
			while((reading = fileInput.read(buffer)) != -1)
			{
				md.update(buffer, 0, reading);
			}
			 byte[] md5_sumBytes = md.digest();
			 StringBuffer strBuffer = new StringBuffer();
			 
			 final int md5_sumBytesLength = md5_sumBytes.length;
			 
			 
			 for(int i = 0; i < md5_sumBytesLength; i++)
			 {
				 strBuffer.append(Integer.toString((md5_sumBytes[i] & 0xFF) + 0x100, 16).substring(1));
			 }
			 
			 fileInput.close();
			 
			 return strBuffer.toString();
	}
	
}
