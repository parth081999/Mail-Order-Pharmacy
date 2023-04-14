package com.mailorderpharmacy.refill.exception;

/**Class for custom exception*/
@SuppressWarnings("serial")
public class SubscriptionIdNotFoundException extends Exception {
	
	/**
	 * @param message
	 */
	public SubscriptionIdNotFoundException(String message) {
		super(message);
	}
			
}
