package fr.songbird.survivalDevKit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
/**
 * A Java class allowing a complete check on the entry
 * She can generates "Checking pattern" without use regexp for user.
 * @author songbird
 * @version 0.8_1-ALPHA (majorRevision.MinorRevision_bugs-developmentPhase)
 * @since TBM-0.3_0-ALPHA
 */
public class CheckEntry {
	
	//###### PRIVATE VARIABLES ######
	private HashMap<Character, AtomicInteger> characterAsciiSet = new HashMap<Character, AtomicInteger>(); 
	private byte occurrenceNumberInt = 0x0;
	private byte occurrenceNumberLowerCase = 0x0;
	private byte occurrenceNumberUpperCase = 0x0;
	private char[] entrySplitted = null;
	private CheckInt checker = null;
	private Pattern foundLowerCase = Pattern.compile("[a-z]");
	private Pattern foundUpperCase = Pattern.compile("[A-Z]");
	private ArrayList<Integer> asciiCode = new ArrayList<Integer>(); 
	
	//###### PUBLIC VARIABLES ######
	
	
	
	
	
	//###### CONSTRUCTOR ######
	
	
	public CheckEntry(){}
	
	
	//############ PRIVATE METHODS ############
	
	

	
	private void initializeOccurrenceTab(int[] oT){
		int i = 0;
		Iterator<Map.Entry<Character, AtomicInteger>> iterator = characterAsciiSet.entrySet().iterator();
		while(i<oT.length && iterator.hasNext()){
			Map.Entry<Character, AtomicInteger> cAs = iterator.next();
			oT[i] = cAs.getValue().get();
			i++;
		}
	}
	
	
	private void balanceOfParsing(){
		System.out.println("La chaine de caracteres possede:  \n"
				+getOccurrenceNumberInt()+" chiffres.\n"
				+getOccurenceNumberLowerCase()+" minuscules.\n"
				+getOccurrenceNumberUpperCase()+" majuscules.\n");
	}
	/**
	 * @param character
	 * @return
	 */
	private boolean heIsAlreadyPresent(char character){
		if(asciiCode.isEmpty()){
			return false;
		}
		for(Integer integer : asciiCode){
			if(character == integer){
				return true;
			}
		}
		return false;
	}
	
	
	//############ PUBLIC METHODS #############
	
	/**
	 * 
	 * @param input
	 * @param getBalanceOfParsing 
	 */
	public void entryChecking(String theEntry, boolean getBalanceOfParsing){
		entrySplitted = theEntry.toCharArray();
		checker = new CheckInt();
		
		for(byte i = 0x0; i<entrySplitted.length; i++){
			if(checker.isInt(entrySplitted[i])){
				occurrenceNumberInt++;
			}
			else{
				if(foundLowerCase.matcher(Character.toString(entrySplitted[i])).matches()){
					occurrenceNumberLowerCase++;
				}
				else if(foundUpperCase.matcher(Character.toString(entrySplitted[i])).matches()){
					occurrenceNumberUpperCase++;
				}
			}
		}
		if(getBalanceOfParsing){
			balanceOfParsing();
		}
	}

	public void analyzeAndCountOccurrenceLetter(char[] word){
		for(char caract : word){
			if(heIsAlreadyPresent(caract)){
				//System.out.println("Repetition de "+caract);
				characterAsciiSet.get(caract).incrementAndGet();
				//System.out.println(caract+":"+characterAsciiSet.get(caract));
			}
			else{
				asciiCode.add((int)caract);
				//System.out.println("Nouvelle lettre "+caract);
				characterAsciiSet.put(caract, new AtomicInteger(0x1));
			}
			//System.out.println("characterAsciiSet: "+characterAsciiSet);
		}	
	}
	
	public boolean capableToBeSuggested(char[] wordInFile, char[] inputKeyBoard){
		
		if(inputKeyBoard[0] == wordInFile[0]){
			return true; 
		}
		return false;
	}
	
	public AtomicInteger getCharacterAsciiSetKey(char key){
		return characterAsciiSet.get(key);
	}
	
	/**
	 * 
	 * @return the occurrence number of integers.
	 */
	public byte getOccurrenceNumberInt(){
		return this.occurrenceNumberInt;
	}
	/**
	 * 
	 * @return the occurrence number of lower cases.
	 */
	public byte getOccurenceNumberLowerCase(){
		return this.occurrenceNumberLowerCase;
	}
	/**
	 * 
	 * @return the occurrence number of upper cases.
	 */
	public byte getOccurrenceNumberUpperCase(){
		return this.occurrenceNumberUpperCase;
	}
	
	public void setDefaultPattern(byte ONI, byte ONLC, byte ONUC){
		this.occurrenceNumberInt = ONI; //Halo fanboys will understand ! :p
		this.occurrenceNumberLowerCase = ONLC;
		this.occurrenceNumberUpperCase = ONUC;
	}
	
	public byte getOccurrenceNumberSum(){
		return (byte)(occurrenceNumberInt + occurrenceNumberLowerCase + occurrenceNumberUpperCase);
	}
	
	public boolean compairToOccurrenceArray(byte[] occurrenceArray){
		boolean identical = true;
		byte[] methodArray = new byte[]{getOccurrenceNumberInt(), getOccurenceNumberLowerCase(), getOccurrenceNumberUpperCase()};
		for(byte i = 0x0; i<occurrenceArray.length; i++){
			if(methodArray[i] != occurrenceArray[i]){
				return !identical;
			}
		}
		return identical;
	}
	
	public Collection<AtomicInteger> getValuesOfCharacterAsciiSet(){
		return characterAsciiSet.values();
	}
	
	
	/**
	 * 
	 * @param word
	 * @return The occurrence number of the dominant letter. But, if all occurrence numbers are identical, so method returns -1.
	 */
	public int getOccurrenceDominantLetter(char[] word){
		analyzeAndCountOccurrenceLetter(word);
		int[] occurrenceTab = new int[word.length];
	    boolean interversion = true;
	    int tabLength = occurrenceTab.length;
	    int intermediaire = 0;
	    
	    initializeOccurrenceTab(occurrenceTab);
	    
	    //Tri a bulles
	    while(tabLength > 0 && interversion){
	          interversion = false;
	         
	       for(int i = 0; i<tabLength-1; i++){
	          if(occurrenceTab[i] > occurrenceTab[i+1]){
	              intermediaire = occurrenceTab[i];
	              occurrenceTab[i] = occurrenceTab[i+1];
	              occurrenceTab[i+1] = intermediaire;
	              interversion = true;
	          }
	         
	       }
	      
	       tabLength = tabLength-1;
	      
	    }
	    System.out.println("OccurrenceTab: "+occurrenceTab);
	    return occurrenceTab[occurrenceTab.length-1];
	}
	
	public HashMap<Character, AtomicInteger> getCurrentInstanceCharacterAsciiSet(){
		return characterAsciiSet;
	}
	
	public ArrayList<Integer> getCurrentInstanceAsciiCode(){
		return asciiCode;
	}
	
	public static void main(String[] args){
		/**
		CheckEntry ck = new CheckEntry();
		CheckEntry machin = new CheckEntry();
		char[] cuisine = new String("Prout").toCharArray();
		char[] cortana = new String("Cortana").toCharArray();
		int dominantLetterMachin = machin.getOccurrenceDominantLetter(cuisine);
		int dominantLetterCk = ck.getOccurrenceDominantLetter(cortana);
		
		if(cortana[0] == cuisine[0] && dominantLetterCk == dominantLetterMachin){
			System.out.println("Les deux mots possedent le meme nombre d'occurrences concernant la lettre dominante et commence par la meme lettre, mais cette derniere n'est peut etre pas identique");
		}
		else{
			System.out.println("Ils ne possedent meme pas le meme nombre d'occurrences, inutile d'essayer de suggerer le mot Cortana");
		}*/
	    CheckEntry ck = new CheckEntry();
	    ck.analyzeAndCountOccurrenceLetter(new String("Cortana").toCharArray());
	}
}
