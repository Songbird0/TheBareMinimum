package fr.songbird.survivalDevKit;

import java.util.regex.Pattern;
/**
 * A Java class allowing a complete check on the entry
 * She can generates "Checking pattern" without use regexp for user.
 * @author songbird
 * @version 0.6_0-ALPHA (majorRevision.MinorRevision_bugs-developmentPhase)
 * @since TBM-0.2_0-ALPHA
 */
public class CheckEntry {
	
	//###### PRIVATE VARIABLES ######
	
	
	private byte occurrenceNumberInt = 0x0;
	private byte occurrenceNumberLowerCase = 0x0;
	private byte occurrenceNumberUpperCase = 0x0;
	private char[] entrySplitted = null;
	private CheckInt checker = null;
	private Pattern foundLowerCase = Pattern.compile("[a-z]");
	private Pattern foundUpperCase = Pattern.compile("[A-Z]");
	
	//###### PUBLIC VARIABLES ######
	
	
	
	
	
	//###### CONSTRUCTOR ######
	
	
	public CheckEntry(){}
	
	
	//############ PRIVATE METHODS ############
	private void balanceOfParsing(){
		System.out.println("La chaine de caracteres possede:  \n"
				+getOccurrenceNumberInt()+" chiffres.\n"
				+getOccurenceNumberLowerCase()+" minuscules.\n"
				+getOccurrenceNumberUpperCase()+" majuscules.\n");
	}
	
	
	//############ PUBLIC METHODS #############
	
	public void entryChecking(String theEntry, boolean getBalanceOfParsing){
		entrySplitted = theEntry.toCharArray();
		checker = new CheckInt();
		
		for(byte i = 0x0; i<entrySplitted.length; i++){
			if(checker.isInt(entrySplitted[i])){
				occurrenceNumberInt++;
			}
			else{
				if(foundLowerCase.matcher(Character.toString(entrySplitted[i])).matches()){ //if this character is an lower case...
					occurrenceNumberLowerCase++;
				}
				else if(foundUpperCase.matcher(Character.toString(entrySplitted[i])).matches()){//if this character is an upper case...
					occurrenceNumberUpperCase++;
				}
			}
		}
		if(getBalanceOfParsing){
			balanceOfParsing();
		}
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
	
	/**
	public static void main(String[] args){
		new CheckEntry("Trust me, I'm programer !");
	}*/
}
