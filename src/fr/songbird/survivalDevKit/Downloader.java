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
 * @version 1.1_2-BETA
 * @author songbird
 * @since TBM-0.7_0-ALPHA
 */
public class Downloader{
	
	//###### PRIVATE VARIABLES ######
	
	
	private static final BasicLogger logger = LoggerFactory.getLogger(Downloader.class);
	private URL racine = null;
    private String fnw = null;
    private InputStream in = null;
    URLConnection urlcFile = null;
    private byte[] buffer = new byte[1024];
    private long fileSize = 0L;
    private FileOutputStream fileToWrite;
    private String pathDef = null;
    private int reading = 0; 
    private File fileDownloaded = null;
    
    //###### PUBLIC VARIABLES ######
    
    
    
    //###### CONSTRUCTOR ######
    
    
  public Downloader(String url, String fileNameWritten, String[] repositories){
      fnw = fileNameWritten;
	  pathDef = Downloader.buildOnlyPath(new String(System.getProperty("user.home"))+File.separator, repositories);
      fileDownloaded = new File(pathDef+fnw);
	  logger.log(LogLevel.INFO, "Construction du chemin "+pathDef);
	  logger.log(LogLevel.INFO, "Creation du fichier "+fnw);
	  try {
		fileDownloaded.createNewFile();
	  } catch (IOException exception2) {
		exception2.printStackTrace();
	  }
	  try {
		fileToWrite = new FileOutputStream(fileDownloaded);
	  } catch (FileNotFoundException exception0) {
		exception0.printStackTrace();
	  }
      try{
        racine = new URL(url);
        getFile(racine);
      }catch(MalformedURLException exception1){
        exception1.printStackTrace();
      }
  }
  
  public Downloader(String url, String fileNameWritten, final File REPOSITORIES){
      fnw = fileNameWritten;
      fileDownloaded = new File(REPOSITORIES.getPath()+File.separator+fnw);
	  logger.log(LogLevel.INFO, "Creation du fichier "+fnw);
	  try {
		fileDownloaded.createNewFile();
	  } catch (IOException exception2) {
		exception2.printStackTrace();
	  }
	  try {
		fileToWrite = new FileOutputStream(fileDownloaded);
	  } catch (FileNotFoundException exception0) {
		exception0.printStackTrace();
	  }
      try{
        racine = new URL(url);
        getFile(racine);
      }catch(MalformedURLException exception1){
        exception1.printStackTrace();
      }
  }

  //###### PRIVATE METHODS ######
  
  
  private void getFile(URL fileUrl) {
	  try{
		  logger.log(LogLevel.INFO, "Tentative de connexion vers: "+fileUrl+".");
	      urlcFile = fileUrl.openConnection();
	  }catch(IOException ioexception0){
		  ioexception0.printStackTrace();
	  }
	  fileSize = urlcFile.getContentLengthLong();
	  logger.log(LogLevel.INFO, "Taille du fichier: "+fileSize+" octets.");
	  try{
		  in = urlcFile.getInputStream();
	  }catch(IOException ioexception1){
		  ioexception1.printStackTrace();
	  }
	  
	  logger.log(LogLevel.INFO, "Téléchargement en cours...");
	  
	  try{
		  while((reading = in.read(buffer)) > 0){
			  logger.log(LogLevel.INFO, (fileSize -= reading)+" octets restants.");
			  fileToWrite.write(buffer, 0, reading);
		  }
	  }catch(IOException ioexception2){
		  ioexception2.printStackTrace();
	  }finally{
		  try{
			  fileToWrite.flush();
			  fileToWrite.close();
			  in.close();
		  }catch(IOException ioexception3){
			  ioexception3.printStackTrace();
		  }
	  }
	  
	  
	  
  }
  
  
  //###### PUBLIC METHODS ######
  

  /**
   * 
   * @return get file name written 
   */
  public String getFNW(){
	  return this.fnw;
  }
  
  /**
   * Builds and returns the path in String.
   * @param path
   * @param repositories
   * @return the path.
   */
  public static String buildOnlyPath(String positionOfRepositories, String[] repositories){
	  for(int i = 0x0; i<repositories.length; i++){
		  positionOfRepositories += repositories[i]+File.separator;
	  }
	  new File(positionOfRepositories).mkdirs();
	  return positionOfRepositories;
  } 
}
