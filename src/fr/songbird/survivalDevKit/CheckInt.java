package fr.songbird.survivalDevKit;

/**
 * <p>
 * PasswordGenerator<br/>
 * Copyright (C) 2015 Songbird<br/>
 * Ce programme est libre, vous pouvez le redistribuer et/ou le modifier selon les termes de la Licence Publique Générale GNU publiée par la Free Software Foundation (version 2 ou bien toute autre version ultérieure choisie par vous).
 * Ce programme est distribué car potentiellement utile, mais SANS AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de commercialisation ou d'adaptation dans un but spécifique. 
 * Reportez-vous à la Licence Publique Générale GNU pour plus de détails.
 * Vous devez avoir reçu une copie de la Licence Publique Générale GNU en même temps que ce programme ; 
 * si ce n'est pas le cas, écrivez à la Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, États-Unis.<br/><br/>
 * 
 * Check if the parameter is an integer or not.
 * </p>
 * @author songbird
 * @version 0.2_1-ALPHA
 * @since TBM-0.1_0-ALPHA
 */
public class CheckInt {
	
	
	//###### PRIVATE VARIABLES ######
	
	
	//###### PUBLIC VARIABLES ######
	
	
	//###### CONSTRUCTOR ###### 
	
	
	public CheckInt(){}
	
	//############ PRIVATE METHODS ############
	
	
	
	
	//############ PUBLIC METHODS ############
	
	
	
	/**
	 * This method works with one character only.
	 * @param character
	 * @return true if the entry is  an integer, else false.
	 */
	public boolean isInt(char character){ //Or not is int, that's the question...
		if(Character.isDigit(character)){
			return true;
		}
		return false;
	}
	
	/**
	 * Method isInt overloaded for to check if input (String) is an integer.
	 * @param entryToCheck
	 * @return true if input is an integer, else false.
	 */
	public boolean isInt(String entryToCheck){
		char[] entrySplitted = entryToCheck.toCharArray();
		boolean isInt = true;
		for(int i = 0; i<entrySplitted.length-1; i++){
			if(!Character.isDigit(entrySplitted[i]) || Integer.parseInt(Character.toString(entrySplitted[i])) == 0){
				isInt = false;
				return isInt;
			}
		}
		return isInt;
	}
	
	/**
	public void yolo(char caract, int entier){
		boolean machin = caract==entier;
		System.out.println(caract+" et "+entier+" se valent ? "+machin);
	}*/
}
