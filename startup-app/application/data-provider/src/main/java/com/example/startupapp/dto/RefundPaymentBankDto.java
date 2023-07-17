package com.example.startupapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object to define the refund for the bank.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class RefundPaymentBankDto {

	/**
	 * Order Identifier
	 */
	@NotNull
	private Long orderId;


	/**
	 * Refund Reason
	 */
	@NotNull
	private String reason;

}
