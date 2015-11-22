package fr.songbird.survivalDevKit;

/**
 * Class that makes of euclidean divisions.
 * @author songbird
 * @version 0.2_1-RELEASE
 *
 */
public class EuclideanDivision {
	
	//###### PRIVATE VARIABLES ######
	
	
    private int quotient = 0x0;
    private int residue = 0x0;
    
    
    
    //###### PUBLIC VARIABLES ######
    
    
    
    //###### CONSTRUCTOR ######
    
    public EuclideanDivision(){
    	
    }
    
    
    //###### PRIVATE VARIABLES ######

    
    private void setResidue(int number, int divisor){
    	this.residue = number%divisor;
    }
    
    private void setQuotient(int number, int divisor){
    	this.quotient = number/divisor;
    }
    
    //###### PUBLIC VARIABLES ######
    
    
    public void euclideanDivision(int number, int divisor){
    	setQuotient(number, divisor);
    	setResidue(number, divisor);
    }
    
    public int getResidue(){
    	return this.residue;
    }
    
    public int getQuotient(){
    	return this.quotient;
    }
}
