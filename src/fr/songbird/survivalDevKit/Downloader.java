package fr.songbird.survivalDevKit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import net.wytrem.logging.BasicLogger;
import net.wytrem.logging.LoggerFactory;
import net.wytrem.logging.LogLevel;

/**
 * The downloader class allows the download any file.
 * @version 0.4_2-ALPHA
 * @author songbird
 * @since TBM-0.6_2-ALPHA
 */
public class Downloader{
	private static final BasicLogger logger = LoggerFactory.getLogger(Downloader.class);
	private String definitivePath;
	private String[] repositories = null;
    String fnw = null;
  public Downloader(String url, String fileNameWrited, String[] repositories){
	  this.repositories = repositories;
      try{
        fnw = fileNameWrited;
        URL racine = new URL(url);
        getFile(racine);
      }catch(MalformedURLException exception5){
        exception5.printStackTrace();
      }
  }

  public void getFile(URL u){
	  	String pathf = null;
        FileOutputStream WritenFile = null;
        InputStream in = null;
        URLConnection urlc = null;
        String FileName = null;
        pathf = null;
        File pathdef = null;
    try{
	    urlc = u.openConnection();
	    logger.log(LogLevel.INFO, "Connexion...");
	    in = urlc.getInputStream();
	    logger.log(LogLevel.INFO, "Ouverture d'un flux en entree...");
	    FileName = u.getFile();
	    logger.log(LogLevel.DEBUG, "Nom du fichier: "+FileName);
	    FileName = FileName.substring(FileName.lastIndexOf('/')+1);
	    logger.log(LogLevel.DEBUG, FileName);
	    pathf = new String(System.getProperty("user.home")+File.separator);
	    //+".PasswordGenerator"+File.separator+"Informations"+File.separator
	    for(int i = 0x0; i<repositories.length; i++){
	    	pathf += repositories[i]+File.separator;
	    }
        try{
        	pathdef = new File(pathf);
        	setDefinitivePath(pathf);
        	pathdef.mkdirs();
        	new File(pathdef+File.separator+fnw).createNewFile();
        	logger.log(LogLevel.INFO, "Chemin construit: "+pathdef);
        	try{
        		WritenFile = new FileOutputStream(pathdef+File.separator+fnw);
        	}catch(FileNotFoundException exception6){
        		exception6.printStackTrace();
        	}
		    logger.log(LogLevel.INFO, "Recuperation du fichier a ecrire: "+WritenFile);
		    byte[] buff = new byte[1024];
		    logger.log(LogLevel.INFO, "Declaration du tampon.");
		    int BytesNumber = in.read(buff);
		    logger.log(LogLevel.INFO, "Téléchargement...");
		    while(BytesNumber>0){
		      WritenFile.write(buff, 0, BytesNumber);
		      BytesNumber = in.read(buff);
		    }
		    logger.log(LogLevel.INFO, "Fin téléchargement.");
        }catch(IOException exception1){
            exception1.toString();
        }finally{WritenFile.flush(); WritenFile.close();}
    }catch(Exception exception0){
        exception0.printStackTrace();
    }finally{
    	try{in.close();}catch(IOException exception2){exception2.printStackTrace();}
    }
  }
  
  private final void setDefinitivePath(String path){
		  this.definitivePath = path;
  }
  
  public String getDefinitivePath(){
	  return this.definitivePath;
  }
  public String getFNW(){
	  return this.fnw;
  }
 
 
}
