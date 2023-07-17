package com.example.startupapp.mapper;

import com.example.startupapp.dto.PaymentResponseBankDto;
import com.example.startupapp.dto.payments.RefundPaymentDto;
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
	 * @param createRefundResponse {@link PaymentResponseBankDto}
	 * @param refundPaymentDto {@link RefundPaymentDto}
	 * @return {@link RefundPaymentResponseDto}
	 */
	public static RefundPaymentResponseDto mapCreateRefundResponse(final PaymentResponseBankDto createRefundResponse,
																   final RefundPaymentDto refundPaymentDto){

		return RefundPaymentResponseDto
				.builder()
				.orderId(refundPaymentDto.getOrderId())
				.transactionId(refundPaymentDto.getTransactionId())
				.status(createRefundResponse.getStatus())
				.message(createRefundResponse.getMessage())
				.value(refundPaymentDto.getValue())
				.build();
	}

}
