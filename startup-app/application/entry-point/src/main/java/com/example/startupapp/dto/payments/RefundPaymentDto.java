package com.example.startupapp.dto.payments;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object to define the refund.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class RefundPaymentDto {

	/**
	 * Order identifier
	 */
	@NotNull
	private Long orderId;

	/**
	 * Refund reason
	 */
	@NotNull
	private String reason;

}
