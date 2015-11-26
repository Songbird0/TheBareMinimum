package fr.songbird.survivalDevKit;

import java.io.*;
import net.wytrem.logging.*;

/**
 * I'm happy to present you my primitive Corrector !
 * He can to correct words that your file contains ! (he's not case sensitive currently)
 * @author songbird
 * @version 0.3_0-ALPHA
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
	private CheckEntry checkWOTF = new CheckEntry();
	private CheckEntry checkIKB = new CheckEntry();
    private String inputKeyBoard = null;
	private int dominantOccurrenceLetterWOTF = 0;
	private int dominantOccurrenceLetterIKB = 0;
	private boolean stopResearch = false;
	
	//###### PUBLIC VARIABLES ######
	
	
	//###### CONSTRUCTOR ######
	
	public Corrector(String fileName, String[] directoriesName){
		File directoriesPath = new File(fileDirectory = Downloader.buildOnlyPath(new String(System.getProperty("user.home")+File.separator), 
				directoriesName));
		directoriesPath.mkdirs();
		orthographyFile = new File(fileDirectory+fileName);
	}
	
	//###### PRIVATE METHODS ######

	private void setDominantOccurrenceLetterWOTF(int DOLW){
		this.dominantOccurrenceLetterWOTF = DOLW;
	}
	
	private void setDominantOccurrenceLetterIKB(int DOLI){
		this.dominantOccurrenceLetterIKB = DOLI;
	}
	
	private void searchCoherentSetKeyValue(char[] wordOfTheFile, char[] inputKeyBoard){ //Cette methode renverra une chaine de caractere (le mot suggere)
		this.pertinentWord = new String(wordOfTheFile);
		this.inputKeyBoard = new String(inputKeyBoard);
		int dominantOccurrenceLetterWOTF = checkWOTF.getOccurrenceDominantLetter(pertinentWord.toCharArray());
		setDominantOccurrenceLetterWOTF(dominantOccurrenceLetterWOTF);
		int dominantOccurrenceLetterIKB = checkIKB.getOccurrenceDominantLetter(this.inputKeyBoard.toCharArray());
		setDominantOccurrenceLetterIKB(dominantOccurrenceLetterIKB);
		
		if(!areIdentical(pertinentWord, this.inputKeyBoard)){
			if(checkWOTF.capableToBeSuggested(wordOfTheFile, inputKeyBoard) 
					&& this.dominantOccurrenceLetterWOTF == this.dominantOccurrenceLetterIKB){
				System.out.println("Capable to be suggested");
				if(identicalDominantLetter(wordOfTheFile, inputKeyBoard, this.dominantOccurrenceLetterWOTF, this.dominantOccurrenceLetterIKB)){
					System.out.println("Vous avez saisi le mot '"+this.inputKeyBoard+"' mais il ne semble pas correct.\nVous vouliez dire '"+this.pertinentWord+"' ?");
					stopResearch = true;
				}
			}
		}
		else{
			System.out.println("Rien a suggerer, les deux mots sont identiques.");
			stopResearch = true;
		}
		
	}
	
    //TODO Petit rappel avant que je n'oublie !
	//La table de hashage prend tous les mots à la suite des autres, ce qui fait grandir la Hashmap et deregle totalement le systeme !
	//Il faut trouver un moyen pour qu'elle puisse prend un seul mot a la fois !
	
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
			int dominantOccurrenceLetterIKB){
		for(Character caract : WOTF){
			if(checkWOTF.getCharacterAsciiSetKey(caract).get() == dominantOccurrenceLetterWOTF){
				commonCharacterWOTF = caract;
				System.out.println("[WOTF]: "+caract+" est la lettre dominante.");
			}
			else{
				commonCharacterWOTF = 0;
			}
		}
		for(Character caract : IKB){
			if(checkIKB.getCharacterAsciiSetKey(caract).get() == dominantOccurrenceLetterIKB){
				System.out.println("[IKB]: "+caract+" est la lettre dominante.");
				commonCharacterIKB = caract;
			}
			else{
				commonCharacterIKB = 0;
			}
		}
		
		
		if((commonCharacterWOTF != 0 && commonCharacterIKB != 0) && commonCharacterWOTF == commonCharacterIKB){
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
				while((pertinentWord = buff.readLine()) != null && stopResearch == false){
					searchCoherentSetKeyValue(pertinentWord.toCharArray(), word.toCharArray());
					System.out.println("tracker rearOrthographyFile");
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
	
	public static void main(String[] args){
		Corrector corr = new Corrector(new String("ofFile"), new String[]{".Corrector", "OF"});
		ScannerHM scan = new ScannerHM(System.in);
		System.out.print("Saisissez un mot: ");
		corr.readOrthographyFile(scan.ReadInputKeyboard());
	}
}
