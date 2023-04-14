package com.mailorderpharmacy.subscription.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mailorderpharmacy.subscription.entity.ErrorMessage;

/**Class to handle all the exceptions*/

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * @param invalidTokenException
	 * @return
	 */
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorMessage> invalidTokenException(InvalidTokenException invalidTokenException) {
		return new ResponseEntity<>(
				new ErrorMessage(HttpStatus.UNAUTHORIZED, LocalDateTime.now(), invalidTokenException.getMessage()),
				HttpStatus.UNAUTHORIZED);
	}

	/**
	 * @param subscriptionListEmptyException
	 * @return
	 */
	@ExceptionHandler(SubscriptionListEmptyException.class)
	public  ResponseEntity<ErrorMessage> subscriptionListEmptyException(SubscriptionListEmptyException subscriptionListEmptyException) {
		return new ResponseEntity<>(
				new ErrorMessage(HttpStatus.NOT_FOUND, LocalDateTime.now(), subscriptionListEmptyException.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	/**
	 * @return
	 */
	@ExceptionHandler(feign.RetryableException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ErrorMessage serviceUnavailableException() {
		return new ErrorMessage(HttpStatus.SERVICE_UNAVAILABLE, LocalDateTime.now(), "Temporarily service unavailable");
	}

}