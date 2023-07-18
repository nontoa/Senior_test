package com.example.startupapp.dto.payments;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object to define the payment.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class CreatePaymentDto {

	/**
	 * One of the available payment methods
	 * listed above
	 */
	@NotNull
	@Pattern(regexp = "^(VISA|MASTERCARD|AMEX)$")
	private String paymentMethod;

	/**
	 * Name that will receive the payment
	 */
	@NotNull
	private String merchantName;

	/**
	 * Value amount of the payment
	 */
	@NotNull
	private BigDecimal value;

	/**
	 * Currency code (ISO 4217 alpha-3)
	 */
	@NotNull
	private String currency;

	/**
	 * Payment's country
	 */
	@NotNull
	private String country;

	/**
	 * Card to process the payment
	 */
	@NotNull
	private CardDto card;

	/**
	 * Who makes the payment
	 */
	@NotNull
	private BuyerDto buyer;

}
