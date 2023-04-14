package com.mailorderpharmacy.refill.exception;

/**Class for custom exception*/
@SuppressWarnings("serial")
public class InvalidTokenException extends Exception {

	/**
	 * @param message
	 */
	public InvalidTokenException(String message){
		super(message);
	}
}
