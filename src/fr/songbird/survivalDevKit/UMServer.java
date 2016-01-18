package fr.songbird.survivalDevKit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
	private File CLIENTPATHFOLDER_SERVER;

	
	@SuppressWarnings("unchecked")
	public UMServer()
	{
		
	}
	
	
	//###### PRIVATE METHODS ######

	
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
	
	
	private void research(File[] files)
	{
		FileWriter writer = null;
		try {
			writer = new FileWriter(CLIENTPATHFOLDER_SERVER.getPath()+"arb.json");
		} catch (IOException ioe0) {
			ioe0.printStackTrace();
		}
		
		for(File file : files)
		{
			if(file.isDirectory())
			{
				research(file.listFiles());
			}
			else
			{
				
			}
		}
	}
	
	
	//###### PUBLIC METHODS ######
	

	
	public void setClientPathFolder(final String DOMAIN_NAME, String...repositories)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(File.separator);
		builder.append(repositories[0]);
		builder.append(File.separator);
		builder.append(repositories[1]);
		builder.append(File.separator);
		builder.append(repositories[2]);
		builder.append(File.separator);
		builder.append(DOMAIN_NAME);
		builder.append("htdocs");
		builder.append(File.separator);
		builder.append("client");
		builder.append(File.separator);
		this.CLIENTPATHFOLDER_SERVER = new File(builder.toString());
	}
	
	
	
	public String getMD5Sum(final File target_file) throws NoSuchAlgorithmException, IOException
	{
			MessageDigest md = null;

				md = MessageDigest.getInstance("MD5");

			
			FileInputStream fileInput = null;
				fileInput = new FileInputStream(target_file);

			
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
