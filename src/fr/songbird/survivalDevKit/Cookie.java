package fr.songbird.survivalDevKit;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.Serializable;


/**
 * 
 * @author songbird
 * @version 0.3_0-ALPHA
 * @since TBM-0.1_0-ALPHA
 */
public class Cookie implements Serializable{
	
	
	//###### PRIVATE VARIABLES ######
	
	
	/**
	 * the serial version UID.
	 */
	private static final long serialVersionUID = -1937255528442757224L;
	
	/**
	 * Path of the serialized file.
	 */
	private static File pathFileSerial = null;
	
	private String fileName;
	
	private String pathInString = null;
	
	private String lang;
	
	
	//###### PUBLIC VARIABLES ######
	
	public Cookie(){
	}
	
	//###### PRIVATE METHODS ######
	

	
	private  void setCookiePath(String path){
		this.pathInString = path;
	}
	
	
	//###### PUBLIC METHODS ######
	
	public File buildingOfThePath(String[] repositories, String fileName){

		this.fileName = fileName;
		String path = new String(System.getProperty("user.home")+File.separator);
		for(int i = 0x0; i<repositories.length; i++){
			path += repositories[i]+File.separator;
		}
		setCookiePath(path);
		new File(path).mkdirs();
		return new File(path+fileName);
	}
	
	public void setLang(String lang){
		this.lang = lang;
	}
	
	public String getLang(){
		return lang;
	}
	
	public String getCookiePath(){
		return pathInString;
	}
	
	/**
	 * Check and creates the cookie if doesn't exits.
	 * @return true if the serialized file is installed.
	 */
	public boolean heIsInstalled(){
		File directory = new File(getCookiePath()+fileName);
		if(!directory.exists()){
			System.out.println("Le cookie n'existe pas.\n"
					+"Creation du cookie...");
			serializeCookies(new Cookie(), getPathFileSerial());
		}
		return true;
	}
	/**
	 * 
	 * @return path of the file serialized.
	 */
	public static File getPathFileSerial(){
		return Cookie.pathFileSerial;
	}
	
	public static void setPathFileSerial(File PFS){
		Cookie.pathFileSerial = PFS;
	}
	
	public static void serializeCookies(Cookie cookie, File pathFileSerial){
		FileOutputStream fileSerial = null;
		ObjectOutputStream objectSerial = null;
		try{
			pathFileSerial.createNewFile();
		}catch(IOException exception5){
			exception5.printStackTrace();
		}
		try{
			fileSerial = new FileOutputStream(pathFileSerial);
			try{
				objectSerial = new ObjectOutputStream(fileSerial);
				try{
					objectSerial.writeObject(cookie);
				}catch(IOException exception2){
					exception2.printStackTrace();
				}
			}catch(IOException exception1){
				exception1.printStackTrace();
			}finally{
				try{
					objectSerial.flush();
					objectSerial.close();
				}catch(IOException exception3){
					exception3.printStackTrace();
				}
			}
		}catch(FileNotFoundException exception0){
			exception0.printStackTrace();
		}finally{
			try{
				fileSerial.close();
			}catch(IOException exception4){
				exception4.printStackTrace();
			}
		}
	}
	public static Cookie DeserializeCookies(){
		FileInputStream fileDeserial = null;
		ObjectInputStream objectDeserial = null;
		Cookie cookie = null;
		File pathFileDeserial = Cookie.getPathFileSerial();
		
		try{
			fileDeserial = new FileInputStream(pathFileDeserial);
			try{
				objectDeserial = new ObjectInputStream(fileDeserial);
				try{
					cookie = (Cookie)objectDeserial.readObject();
				}catch(ClassNotFoundException exception2){
					exception2.printStackTrace();
				}
			}catch(IOException exception1){
				exception1.printStackTrace();
			}finally{
				try{
					objectDeserial.close();
				}catch(IOException exception4){
					exception4.printStackTrace();
				}
			}
		}catch(FileNotFoundException exception0){
			exception0.printStackTrace();
		}finally{
			try{
				fileDeserial.close();
			}catch(IOException exception3){
				exception3.printStackTrace();
			}
		}
		
		
		return cookie;
	}
}
