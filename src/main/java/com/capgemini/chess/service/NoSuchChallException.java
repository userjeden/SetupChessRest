package com.capgemini.chess.service;

public class NoSuchChallException extends Exception {

	private static final long serialVersionUID = 5328931955662714992L;
	
	public NoSuchChallException(String message) {
		super(message);
	}
}
