package fr.songbird.survivalDevKit;

import java.io.*;
import net.wytrem.logging.*;

/**
 * I'm happy to present you my primitive Corrector !
 * He can to correct words that your file contains ! (he's not case sensitive currently)
 * @author songbird
 * @version 0.2_0-ALPHA
 *
 */
public class Corrector {
	
	//###### PRIVATE VARIABLES ######
	
	private final static BasicLogger logger = LoggerFactory.getLogger(Corrector.class);
	private char commonCharacterWOTF = 0;
	private char commonCharacterIKB = 0;
	private File orthographyFile = null;
	private String pertinentWord = null;
	private String fileDirectory = null;
	
	
	//###### PUBLIC VARIABLES ######
	
	
	//###### CONSTRUCTOR ######
	
	public Corrector(String fileName, String[] directoriesName){
		File directoriesPath = new File(fileDirectory = Downloader.buildOnlyPath(new String(System.getProperty("user.home")+File.separator), 
				directoriesName));
		directoriesPath.mkdirs();
		orthographyFile = new File(fileDirectory+fileName);
	}
	
	//###### PRIVATE METHODS ######

	
	private void searchCoherentSetKeyValue(char[] wordOfTheFile, char[] inputKeyBoard){ //Cette methode renverra une chaine de caractere (le mot suggere)
		CheckEntry checkWOTF = new CheckEntry();
		CheckEntry checkIKB = new CheckEntry();
		if(checkWOTF.capableToBeSuggested(wordOfTheFile, inputKeyBoard) 
				&& checkWOTF.getOccurrenceDominantLetter(wordOfTheFile) == checkIKB.getOccurrenceDominantLetter(inputKeyBoard)){
			if(identicalDominantLetter(wordOfTheFile, inputKeyBoard, checkWOTF.getOccurrenceDominantLetter(wordOfTheFile),
					checkIKB.getOccurrenceDominantLetter(inputKeyBoard),
					checkWOTF, checkIKB)){
				System.out.println("L'occurrence dominante de IKB est egale a: "+checkIKB.getOccurrenceDominantLetter(inputKeyBoard)+"\n l'occurrence dominante de WOTF est egale a: "+checkWOTF.getOccurrenceDominantLetter(wordOfTheFile));
				Runtime.getRuntime().exit(0);
			}
		}
	}
	

	
	/**
	 * On cherche si le nombre d'occurrences identiques vaut aussi pour la lettre, les deux mots peuvent avoir autant d'occurrences, mais pas la meme lettre
	 * @param WOTF
	 * @param IKB
	 * @param dominantOccurrenceLetterWOTF
	 * @param dominantOccurrenceLetterIKB
	 * @param instanceWOTF
	 * @param instanceIKB
	 * @return
	 */
	private boolean identicalDominantLetter(char[] WOTF, char[] IKB, int dominantOccurrenceLetterWOTF, //TODO A verifier, la methode est buguée !
			int dominantOccurrenceLetterIKB,
			CheckEntry instanceWOTF, CheckEntry instanceIKB){
		
		
		
		
		
		System.out.println("Occurrence dominante de WOTF: "+dominantOccurrenceLetterWOTF);
		System.out.println("Occurrence dominante de IKB: "+dominantOccurrenceLetterIKB);
		for(Character caract : WOTF){
			if(instanceWOTF.getCharacterAsciiSetKey(caract).get() == dominantOccurrenceLetterWOTF){
				commonCharacterWOTF = caract;
				System.out.println("[WOTF]: "+caract+" est la lettre dominante.");
			}
		}
		for(Character caract : IKB){
			if(instanceIKB.getCharacterAsciiSetKey(caract).get() == dominantOccurrenceLetterIKB){
				System.out.println("[IKB]: "+caract+" est la lettre dominante.");
				commonCharacterIKB = caract;
			}
		}
		
		if(commonCharacterWOTF != 0 && commonCharacterIKB != 0){
			return true;
		}
		return false;
	}
	
	private boolean areIdentical(String WOTF, String IKB){
		return WOTF.equals(IKB);
	}
	
	//###### PUBLIC METHODS ######
	
	public void readOrthographyFile(String word){
		BufferedReader buff = null;
		if(orthographyFile.exists()){
			try{
				buff = new BufferedReader(new InputStreamReader(new FileInputStream(orthographyFile)));
				while((pertinentWord = buff.readLine()) != null){
					searchCoherentSetKeyValue(pertinentWord.toCharArray(), word.toCharArray());
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
		else{
			try {
				logger.warning("Le fichier d'orthographe n'est pas present !\nUn fichier vide a ete cree, veuillez le completer !");
				orthographyFile.createNewFile();
			} catch (IOException exception3) {
				exception3.printStackTrace();
			}
		}
	}
	
	public boolean readOrthographyFile(String word, boolean toSearchIdenticalWord){
		BufferedReader buff = null;
		boolean isFound = false;
		if(orthographyFile.exists()){
			try{
				buff = new BufferedReader(new InputStreamReader(new FileInputStream(orthographyFile)));
				while((pertinentWord = buff.readLine()) != null){
					if(areIdentical(pertinentWord, word)){
						System.out.println(pertinentWord+" et "+word+" sont identiques.");
						return isFound = true;
					}
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
		else{
			try {
				logger.warning("Le fichier d'orthographe n'est pas present !\nUn fichier vide a ete cree, veuillez le completer !");
				orthographyFile.createNewFile();
			} catch (IOException exception3) {
				exception3.printStackTrace();
			}
		}
		return isFound;
	}
}
