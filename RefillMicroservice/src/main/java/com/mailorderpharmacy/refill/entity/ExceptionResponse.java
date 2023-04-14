package com.mailorderpharmacy.refill.entity;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**Model class of the business details*/
public class ExceptionResponse {

	/**
	 * Exception message
	 */
	String messge;
	/**
	 * Error message of Timestamp
	 */
	LocalDateTime timestamp;
	/**
	 * Status of Http 
	 */
	HttpStatus status;
}
