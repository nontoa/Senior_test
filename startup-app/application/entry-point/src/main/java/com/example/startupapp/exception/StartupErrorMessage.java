package com.example.startupapp.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * Startup Error message class
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
public class StartupErrorMessage {


	/**
	 * Status
	 */
	private String status;

	/**
	 *Error code
	 */
	private String code;

	/**
	 * Error message
	 */
	private String message;

}
