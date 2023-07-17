package com.example.startupapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object to define the antifraud request for the antifraud mock.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class AntifraudRequestDto {

	/**
	 * cardholder name
	 */
	@NotNull
	private String cardHolderName;

	/**
	 * Card number
	 */
	@NotNull
	private String cardNumber;

	/**
	 * Card security code
	 */
	@NotNull
	private String cardCsc;

	/**
	 * Card holder's document
	 */
	@NotNull
	private String holderDocument;

	/**
	 * Card expiration month (2-digits)
	 */
	@NotNull
	@Size(min=2, max=2)
	private String month;

	/**
	 * Card expiration year (4-digits)
	 */
	@NotNull
	@Size(min=4, max=4)
	private String year;

}
