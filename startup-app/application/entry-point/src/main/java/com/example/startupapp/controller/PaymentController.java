package com.example.startupapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.startupapp.dto.payments.CreatePaymentDto;
import com.example.startupapp.dto.payments.CreatePaymentResponseDto;
import com.example.startupapp.dto.payments.RefundPaymentDto;
import com.example.startupapp.dto.payments.RefundPaymentResponseDto;
import com.example.startupapp.exception.PaymentException;
import com.example.startupapp.service.IPaymentService;
import com.example.startupapp.constant.RestEndpoint;
import jakarta.validation.Valid;

/**
 * Controller for <code>/create-payments</code> resource endpoint
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@RestController
@RequestMapping(RestEndpoint.PAYMENTS)
public class PaymentController {

	/**
	 * Payment Service
	 */
	private IPaymentService paymentService;

	/**
	 * PaymentController's constructor
	 * @param paymentService Payment Service
	 */
	public PaymentController(final IPaymentService paymentService){
		this.paymentService = paymentService;
	}

	/**
	 * Endpoint to create a payment
	 * @param createPaymentDto CreatePaymentDto received into the request
	 * @return {@link CreatePaymentResponseDto} with the payment data created
	 */
	@PostMapping
	public ResponseEntity<CreatePaymentResponseDto> createPayment(
			@Valid @RequestBody CreatePaymentDto createPaymentDto) throws PaymentException {

		return new ResponseEntity<>(paymentService.createPayment(createPaymentDto),
									HttpStatus.OK);
	}

	/**
	 * Endpoint to refund a payment.
	 * @param refundPaymentDto {@link RefundPaymentDto}
	 * @return {@link RefundPaymentResponseDto}
	*/
	@PostMapping(RestEndpoint.REFUND)
	public ResponseEntity<RefundPaymentResponseDto> refundPayment(
			@Valid @RequestBody RefundPaymentDto refundPaymentDto) throws PaymentException {

		return new ResponseEntity<>(paymentService.refundPayment(refundPaymentDto),
									HttpStatus.OK);
	}

}
