package com.example.startupapp.dto;

import com.example.startupapp.constants.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

/**
 * Data transfer object to define the payment response from the bank.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Jacksonized
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseBankDto {

	/**
	 * Transaction status
	 */
	private TransactionStatus status;

	/**
	 * Transaction Message
	 */
	private String message;

}
