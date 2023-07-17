package com.example.startupapp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.example.startupapp.constants.TransactionStatus;
import com.example.startupapp.constants.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data access object for transactions
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class TransactionDao implements Serializable {

	private String id;

	private Long orderId;

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
