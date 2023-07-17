package com.example.startupapp.dto.payments;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import com.example.startupapp.constants.TransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object to define the refund response.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class RefundPaymentResponseDto {

	/**
	 * Order identifier
	 */
	@NotNull
	private String orderId;

	/**
	 * Transaction ID related to the refund
	 */
	@NotNull
	private String transactionId;

	/**
	 * Status for this refund
	 */
	@NotNull
	private TransactionStatus status;

	/**
	 * Message
	 */
	private String message;

	/**
	 * Transaction value refunded
	 */
	@NotNull
	private BigDecimal value;

}
