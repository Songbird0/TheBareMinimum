package fr.songbird.survivalDevKit;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * 
 *	A Scanner class home made.
 * @author songbird
 * @version 0.1_0-ALPHA
 * @since TBM-0.1_0-ALPHA
 */
public class ScannerHM {
	private BufferedReader reader = null;
	
	/**
	 * CONSTRUCTORS
	 */
	/**
	 * This constructor allowing preparation for to read the input stream.
	 * @param inputstream
	 */
	public ScannerHM(InputStream inputstream){
		if(inputstream.equals(System.in)){
			reader = new BufferedReader(new InputStreamReader(inputstream));
		}
	}
	
	
	/**
	 * 
	 * @return the input keyboard.
	 */
	public String ReadInputKeyboard(){
		String iKeyBoard = null;
		try{
			iKeyBoard = reader.readLine();
		}catch(IOException exception0){
			exception0.toString();
		}
		return iKeyBoard;
	}
	/**
	 * 
	 * @return an integer. Else, catch an NumberFormatException.
	 */
	public int ReadAndParseInt(){
		String iKeyBoard = null;
		try{
			iKeyBoard = reader.readLine();
		}catch(IOException exception1){
			exception1.getMessage();
		}
		char[] tabOfVerif = iKeyBoard.toCharArray();
		for(int i = 0; i<tabOfVerif.length; i++){
			if(!Character.isDigit(tabOfVerif[i])){
				throw new NumberFormatException();
			}
		}
		return Integer.parseInt(iKeyBoard);
	}
	
	/**
	 * 
	 * @return current instance.
	 */
	public ScannerHM getCurrentInstanceSHM(){
		return this;
	}
	
}
