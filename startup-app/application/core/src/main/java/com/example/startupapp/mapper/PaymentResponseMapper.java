package com.example.startupapp.mapper;

import com.example.startupapp.dto.payments.CreatePaymentDto;
import com.example.startupapp.dto.PaymentResponseBankDto;
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
	 * @param createPaymentResponse {@link PaymentResponseBankDto}
	 * @param createPaymentDto {@link CreatePaymentDto}
	 * @return {@link CreatePaymentResponseDto}.
	 */
	public static CreatePaymentResponseDto mapCreatePaymentResponse(
			final PaymentResponseBankDto createPaymentResponse,
			final CreatePaymentDto createPaymentDto){

		return CreatePaymentResponseDto
				.builder()
				.orderId(createPaymentDto.getOrderId())
				.transactionId(createPaymentDto.getTransactionId())
				.status(createPaymentResponse.getStatus())
				.message(createPaymentResponse.getMessage())
				.currency(createPaymentDto.getCurrency())
				.value(createPaymentDto.getValue())
				.build();
	}

}
