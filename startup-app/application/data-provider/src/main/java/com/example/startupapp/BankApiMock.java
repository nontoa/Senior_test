package com.example.startupapp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.startupapp.configuration.BankApiMockConfiguration;
import com.example.startupapp.constants.BankMockEndpoint;
import com.example.startupapp.dto.CreatePaymentBankDto;
import com.example.startupapp.dto.PaymentResponseBankDto;
import com.example.startupapp.dto.RefundPaymentBankDto;

/**
 * Bank Api Mock.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@FeignClient(name = "bankMock", url = "${bankMock.url}", configuration = BankApiMockConfiguration.class)
public interface BankApiMock {

	@PostMapping(value =BankMockEndpoint.PAYMENTS, consumes = MediaType.APPLICATION_JSON_VALUE)
	PaymentResponseBankDto createPayment(
			@RequestBody CreatePaymentBankDto createPaymentBankDto);

	@PostMapping(value = BankMockEndpoint.REFUND, consumes = MediaType.APPLICATION_JSON_VALUE)
	PaymentResponseBankDto refundPayment(
			@RequestBody RefundPaymentBankDto refundPaymentBankDto);


}
