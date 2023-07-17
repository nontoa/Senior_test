package com.example.startupapp.mapper;

import com.example.startupapp.dto.payments.RefundPaymentDto;
import com.example.startupapp.dto.RefundPaymentBankDto;

/**
 * Converts a {@link RefundPaymentDto} to {@link RefundPaymentBankDto}.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class CreateRefundMapper {

	/**
	 * Constructor.
	 */
	private CreateRefundMapper() {
		// Empty constructor.
	}

	/**
	 * Converts {@link RefundPaymentDto} to {@link RefundPaymentBankDto}
	 * @param refundPaymentDto {@link RefundPaymentDto}
	 * @return {@link RefundPaymentBankDto}
	 */
	public static RefundPaymentBankDto mapRefundPayment(final RefundPaymentDto refundPaymentDto){

		return RefundPaymentBankDto
				.builder()
				.orderId(refundPaymentDto.getOrderId())
				.reason(refundPaymentDto.getReason())
				.build();
	}

}
