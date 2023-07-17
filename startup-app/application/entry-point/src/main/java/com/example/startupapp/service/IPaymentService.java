package com.example.startupapp.service;

import com.example.startupapp.dto.payments.CreatePaymentDto;
import com.example.startupapp.dto.payments.CreatePaymentResponseDto;
import com.example.startupapp.dto.payments.RefundPaymentDto;
import com.example.startupapp.dto.payments.RefundPaymentResponseDto;
import com.example.startupapp.exception.PaymentException;

/**
 * Service to create a payment
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public interface IPaymentService {

	/**
	 * Creates a payment.
	 *
	 * @param createPaymentDto Request received.
	 * @return Create Payment response
	 */
	CreatePaymentResponseDto createPayment(final CreatePaymentDto createPaymentDto) throws PaymentException;

	/**
	 * Refunds a payment
	 *
	 * @param refundPaymentDto Request received
	 * @return Create refund response
	 */
	RefundPaymentResponseDto refundPayment(final RefundPaymentDto refundPaymentDto) throws PaymentException;

}
