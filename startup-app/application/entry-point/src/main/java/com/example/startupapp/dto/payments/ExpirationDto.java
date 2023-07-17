package com.example.startupapp.dto.payments;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;

/**
 * Data transfer object to define expiration of payment card.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
public class ExpirationDto {

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
