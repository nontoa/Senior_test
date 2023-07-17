package com.example.startupapp.mapper;

import com.example.startupapp.dao.Transaction;
import com.example.startupapp.dto.payments.RefundPaymentResponseDto;

/**
 * Builds a {@link RefundPaymentResponseDto}.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class RefundResponseMapper {

	/**
	 * Constructor.
	 */
	private RefundResponseMapper() {
		// Empty constructor.
	}

	/**
	 * Builds a {@link RefundPaymentResponseDto}
	 *
	 * @param transaction {@link Transaction}
	 * @return {@link RefundPaymentResponseDto}
	 */
	public static RefundPaymentResponseDto mapCreateRefundResponse(final Transaction transaction){

		return RefundPaymentResponseDto
				.builder()
				.orderId(transaction.getOrderId())
				.transactionId(transaction.getId())
				.status(transaction.getStatus())
				.message(transaction.getMessage())
				.build();
	}

}
