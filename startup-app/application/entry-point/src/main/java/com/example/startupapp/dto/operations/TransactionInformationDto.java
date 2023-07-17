package com.example.startupapp.dto.operations;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.example.startupapp.constants.TransactionStatus;
import com.example.startupapp.constants.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object to define the transaction information.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInformationDto {

	private String Id;

	private String orderId;

	private TransactionType type;

	private String paymentMethod;

	private String merchantName;

	private BigDecimal value;

	private String currency;

	private String buyerName;

	private String buyerDocument;

	private TransactionStatus status;

	private String message;

	private Timestamp creationDate;

}
