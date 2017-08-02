package com.capgemini.chess.enums;

public enum ChallengeStatus {

	POTENTIAL("potential"), 
	THROWN("thrown"), 
	ACCEPTED("accepted"), 
	OUTDATED("outdated"), 
	CLOSED("closed");
	
	
   
    private String value;
    
    ChallengeStatus(String value) {
    	this.value = value;
    }
     
    public String getValue() {
        return value;
    }

}
