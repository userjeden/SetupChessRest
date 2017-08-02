package com.capgemini.chess.service;

public class NoSuchUserException extends Exception {

	private static final long serialVersionUID = 5328931955662714992L;
	
	public NoSuchUserException(String message) {
		super(message);
	}
}
