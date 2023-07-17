package com.example.startupapp.dto.payments;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * Data transfer object to define the buyer.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
public class BuyerDto {

	/**
	 * Buyer's first name
	 */
	@NotNull
	private String firstName;

	/**
	 * Buyer's last name
	 */
	@NotNull
	private String lastName;

	/**
	 * Buyer's document number
	 */
	@NotNull
	private String document;

	/**
	 * Buyer's document type
	 */
	@NotNull
	private String documentType;

	/**
	 * Buyer's email
	 */
	@NotNull
	@Email
	private String email;

	/**
	 * Buyer's phone number
	 */
	@NotNull
	private String phone;

}
