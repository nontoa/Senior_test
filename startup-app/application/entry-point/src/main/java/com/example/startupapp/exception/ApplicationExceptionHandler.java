package com.example.startupapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class to handle exceptions
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * This method handles {@link IllegalArgumentException} exception
	 * @param ex Exception
	 * @param request Request
	 * @return {@link StartupErrorMessage} Error message
	 */
	@ExceptionHandler(value ={IllegalArgumentException.class})
	public ResponseEntity<Object> handleIllegalException(final IllegalArgumentException ex, final WebRequest request){

		return this.handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * This method handles {@link PaymentException} exception
	 * @param ex Exception
	 * @param request Request
	 * @return {@link StartupErrorMessage} Error message
	 */
	@ExceptionHandler(value ={PaymentException.class})
	public ResponseEntity<Object> handlePaymentException(final PaymentException ex, final WebRequest request){

		final var body = StartupErrorMessage
				.builder()
				.status(ex.getStatus())
				.code(HttpStatus.INTERNAL_SERVER_ERROR.name())
				.message(ex.getMessage())
				.build();
		return this.handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * Handles unexpected exception.
	 *
	 * @param ex The exception
	 * @param request The request
	 * @return A general {@link StartupErrorMessage} Error message.
	 */
	@ExceptionHandler(value ={Exception.class})
	public ResponseEntity<Object> handleUnexpectedException(final Exception ex, final WebRequest request){

		final var body = StartupErrorMessage
				.builder()
				.status("error")
				.code(HttpStatus.INTERNAL_SERVER_ERROR.name())
				.message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
				.build();

		return this.handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
															 @Nullable Object body,
															 HttpHeaders headers,
															 HttpStatus status,
															 WebRequest request) {

		var errorMessage = body;
		if (errorMessage == null) {

			errorMessage = StartupErrorMessage
					.builder()
					.status("error")
					.code(status.name())
					.message(ex.getMessage())
					.build();

		}

		return new ResponseEntity<>(errorMessage, headers, status);

	}

}
