package com.example.startupapp.dto.payments;

import java.math.BigDecimal;

import com.example.startupapp.constants.AntifraudStatus;
import com.example.startupapp.constants.TransactionStatus;
import jakarta.validation.constraints.NotNull;
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
	private Long orderId;

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
	 * Status for antifraud evaluation
	 */
	@NotNull
	private AntifraudStatus antifraudStatus;

	/**
	 * Message for antifraud evaluation
	 */
	@NotNull
	private String antifraudMessage;

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
