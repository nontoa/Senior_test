package com.example.startupapp.dto.payments;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object to define the payment card.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class CardDto {

	/**
	 * Card holder name
	 */
	@NotNull
	private String holder;

	/**
	 * Card number
	 */
	@NotNull
	private String number;

	/**
	 * Card security code
	 */
	@NotNull
	private String csc;

	/**
	 * Card holder's document
	 */
	@NotNull
	private String document;

	/**
	 * Expiration
	 */
	@NotNull
	private ExpirationDto expiration;

}
