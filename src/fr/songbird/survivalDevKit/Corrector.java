package fr.songbird.survivalDevKit;

import java.io.*;
import net.wytrem.logging.*;

/**
 * I'm happy to present you my primitive Corrector !
 * He can to correct words that your file contains ! (he's not case sensitive currently)
 * @author songbird
 * @version 0.1_0-ALPHA
 *
 */
public class Corrector {
	
	//###### PRIVATE VARIABLES ######
	
	private final static BasicLogger logger = LoggerFactory.getLogger(Corrector.class);
	private char commonCharacterWOTF = 0;
	private char commonCharacterIKB = 0;
	private File orthographyFile = null;
	private String pertinentWord = null;
	private String fileDirectory = Downloader.buildOnlyPath(new String(System.getProperty("user.home")+File.separator), 
			new String[]{".Corrector", "OF"});
	
	
	//###### PUBLIC VARIABLES ######
	
	
	//###### CONSTRUCTOR ######
	
	public Corrector(){
		
	}
	
	//###### PRIVATE METHODS ######

	
	private void searchAndGetCoherentSetKeyValue(char[] wordOfTheFile, char[] inputKeyBoard){ //Cette methode renverra une chaine de caractere (le mot suggere)
		CheckEntry checkWOTF = new CheckEntry();
		CheckEntry checkIKB = new CheckEntry();
		
		if(checkWOTF.capableToBeSuggested(wordOfTheFile, inputKeyBoard) 
				&& checkWOTF.getOccurrenceDominantLetter(wordOfTheFile) == checkIKB.getOccurrenceDominantLetter(inputKeyBoard)){
			if(areIdentical(wordOfTheFile, inputKeyBoard, checkWOTF.getOccurrenceDominantLetter(wordOfTheFile),
					checkIKB.getOccurrenceDominantLetter(inputKeyBoard),
					checkWOTF, checkIKB)){
				//TODO On applique la derniere instruction pour suggerer le mot que l'on souhaite.
				logger.error("Vous avez saisi: "+inputKeyBoard.toString()+", mais il ne semble pas etre correct...\nVous vouliez dire: "+wordOfTheFile.toString()+" ?");
			}
		}
	}
	
	private boolean areIdentical(char[] WOTF, char[] IKB, int dominantOccurrenceLetterWOTF, 
			int dominantOccurrenceLetterIKB,
			CheckEntry instanceWOTF, CheckEntry instanceIKB){
		for(Character caract: WOTF){
			if(instanceWOTF.getCharacterAsciiSetKey(caract).get() == dominantOccurrenceLetterWOTF){
				commonCharacterWOTF = caract;
			}
		}
		for(Character caract : IKB){
			if(instanceIKB.getCharacterAsciiSetKey(caract).get() == dominantOccurrenceLetterWOTF){
				commonCharacterIKB = caract;
			}
		}
		
		if(commonCharacterWOTF != 0 && commonCharacterIKB != 0){
			return true;
		}
		return false;
	}
	
	
	//###### PUBLIC METHODS ######
	
	
	public void readOrthographyFile(File orthographyFile, String word){
		BufferedReader buff = null;
		try{
			buff = new BufferedReader(new InputStreamReader(new FileInputStream(orthographyFile)));
			while((pertinentWord = buff.readLine()) != null){
				searchAndGetCoherentSetKeyValue(pertinentWord.toCharArray(), word.toCharArray());
			}
		}catch(IOException ioexception0){
			ioexception0.printStackTrace();
		}catch(Exception exception1){
			exception1.printStackTrace();
		}
		finally{
			try {
				buff.close();
			} catch (IOException exception2) {
				exception2.printStackTrace();
			}
		}
	}
}
