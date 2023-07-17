package com.example.startupapp.exception;

import lombok.Getter;

/**
 * Payment Error message class
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Getter
public class PaymentException extends Exception {

	/**
	 * Status
	 */
	private String status;

	/**
	 * Error message
	 */
	private String message;

	public PaymentException(String status, String message){
		super(message);
		this.status = status;
		this.message = message;
	}

}
