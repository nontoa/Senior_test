package com.example.startupapp.mapper;

import com.example.startupapp.dao.Transaction;
import com.example.startupapp.dto.payments.CreatePaymentResponseDto;


/**
 * Builds a {@link CreatePaymentResponseDto}.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class PaymentResponseMapper {

	/**
	 * Constructor.
	 */
	private PaymentResponseMapper() {
		// Empty constructor.
	}

	/**
	 * Builds a {@link CreatePaymentResponseDto}.
	 *
	 * @param transaction {@link Transaction}
	 * @return {@link CreatePaymentResponseDto}.
	 */
	public static CreatePaymentResponseDto mapCreatePaymentResponse(final Transaction transaction){

		return CreatePaymentResponseDto
				.builder()
				.orderId(transaction.getOrderId())
				.transactionId(transaction.getId())
				.status(transaction.getStatus())
				.message(transaction.getMessage())
				.currency(transaction.getCurrency())
				.value(transaction.getValue())
				.antifraudStatus(transaction.getAntifraudStatus())
				.antifraudMessage(transaction.getAntifraudMessage())
				.build();
	}

}
