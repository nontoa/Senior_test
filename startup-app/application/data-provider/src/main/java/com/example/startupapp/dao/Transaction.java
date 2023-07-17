package com.example.startupapp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.example.startupapp.constants.AntifraudStatus;
import com.example.startupapp.constants.TransactionStatus;
import com.example.startupapp.constants.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data access object for transactions
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Table
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {

	@Id
	private String id;

	@Column
	private Long orderId;

	@Column
	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@Column
	private String paymentMethod;

	@Column
	private String merchantName;

	@Column
	private BigDecimal value;

	@Column
	private String currency;

	@Column
	private String buyerName;

	@Column
	private String buyerDocument;

	@Column
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	@Column
	private String message;

	@Column
	@Enumerated(EnumType.STRING)
	private AntifraudStatus antifraudStatus;

	@Column
	private String antifraudMessage;

	@Column
	private Timestamp creationDate;

}
