package com.example.startupapp.dto.payments;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import com.example.startupapp.constants.TransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object to define the payment response.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class CreatePaymentResponseDto {

	/**
	 * Order identifier
	 */
	@NotNull
	private String orderId;

	/**
	 * Transaction ID related to the payment
	 */
	@NotNull
	private String transactionId;

	/**
	 * Status for this payment
	 */
	@NotNull
	private TransactionStatus status;

	/**
	 * Message
	 */
	private String message;

	/**
	 * Message
	 */
	private String currency;

	/**
	 * Transaction value
	 */
	@NotNull
	private BigDecimal value;

}
