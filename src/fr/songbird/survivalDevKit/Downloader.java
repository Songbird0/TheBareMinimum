package fr.songbird.survivalDevKit;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import net.wytrem.logging.BasicLogger;
import net.wytrem.logging.LogLevel;
import net.wytrem.logging.LoggerFactory;

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
  
  public Downloader(String url, String fileNameWritten, String[] repositories, boolean gui){
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
        getFile(racine, gui);
      }catch(MalformedURLException exception1){
        exception1.printStackTrace();
      }
  }

  //###### PRIVATE METHODS ######
  
  
  private void getFile(URL fileUrl) 
  {
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
	  
	  try
	  {
		  while((reading = in.read(buffer)) > 0)
		  {
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
  
  private void getFile(URL fileUrl, boolean guiDL) 
  {
	  final JFrame frame = new JFrame();
	  frame.setLayout(new BorderLayout());
	  int currentBytes = 0;
	  Border border = BorderFactory.createTitledBorder("Téléchargement...");
	  final JProgressBar jpb = new JProgressBar();
	  jpb.setMinimum(0);
	  jpb.setMaximum(100);
	  jpb.setBorder(border);
	  jpb.setStringPainted(true);
	  frame.setPreferredSize(new Dimension(300, 100));
	  frame.getContentPane().add(jpb, BorderLayout.CENTER);
	  frame.addWindowListener(new WindowAdapter()
	  {
		  public void windowClosing(WindowEvent we)
		  {
			  Runtime.getRuntime().exit(0x0);
		  }
	  });
	  frame.pack();
	  frame.setVisible(true);
	  try{
		  logger.log(LogLevel.INFO, "Tentative de connexion vers: "+fileUrl+".");
	      urlcFile = fileUrl.openConnection();
	      fileSize = urlcFile.getContentLengthLong();
	  	  logger.log(LogLevel.INFO, "Taille du fichier: "+fileSize+" octets.");
		  in = urlcFile.getInputStream();
	  }catch(IOException ioexception1){
		  ioexception1.printStackTrace();
	  }
	  
	  logger.log(LogLevel.INFO, "Téléchargement en cours...");

	  try
	  {
		  while((reading = in.read(buffer)) > 0)
		  {
			  currentBytes += reading;
			  System.out.println(currentBytes);
			  jpb.setValue((currentBytes*100)/(int)fileSize);
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
  public static String buildOnlyPath(String positionOfRepositories, String... repositories)
  {
	  for(int i = 0x0; i<repositories.length; i++)
	  {
		  positionOfRepositories += repositories[i]+File.separator;
	  }
	  new File(positionOfRepositories).mkdirs();
	  return positionOfRepositories;
  }
  
  public static void main(String...strings)
  {
	  new Downloader("https://www.dropbox.com/s/fenk2sa2397k5cx/AD_Reforged_1.8_v3a.zip?dl=1", "Test", new String[]{"Prout", "Pouet"}, true);
  }
  
  
}
