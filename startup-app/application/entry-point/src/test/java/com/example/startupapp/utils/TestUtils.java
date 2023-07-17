package com.example.startupapp.utils;

import java.math.BigDecimal;

import com.example.startupapp.constants.TransactionStatus;
import com.example.startupapp.dto.payments.BuyerDto;
import com.example.startupapp.dto.payments.CardDto;
import com.example.startupapp.dto.payments.CreatePaymentDto;
import com.example.startupapp.dto.payments.CreatePaymentResponseDto;
import com.example.startupapp.dto.payments.ExpirationDto;
import com.example.startupapp.dto.payments.RefundPaymentDto;
import com.example.startupapp.dto.payments.RefundPaymentResponseDto;

/**
 * Tests util class to build objects.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class TestUtils {

	public static CreatePaymentDto buildCreatePayment(){

		return CreatePaymentDto
				.builder()
				.paymentMethod("Visa")
				.merchantName("MyStore")
				.value(new BigDecimal(150000))
				.currency("COP")
				.card(buildCard())
				.buyer(buildBuyer())
				.build();
	}

	public static CardDto buildCard(){

		return CardDto
				.builder()
				.holder("Pepito")
				.document("34567856")
				.number("4771346007366020")
				.csc("777")
				.expiration(ExpirationDto
									.builder()
									.month("07")
									.year("2026")
									.build())
				.build();
	}

	public static BuyerDto buildBuyer(){

		return BuyerDto
				.builder()
				.firstName("Pepito")
				.lastName("Perez")
				.document("34567856")
				.documentType("CC")
				.email("pepito@example.com")
				.phone("+5789999998")
				.build();
	}

	public static CreatePaymentResponseDto buildPaymentResponse(){

		return CreatePaymentResponseDto
				.builder()
				.orderId(1L)
				.transactionId("12T34H5")
				.status(TransactionStatus.APPROVED)
				.message("Successful approved")
				.currency("COP")
				.value(new BigDecimal(150000))
				.build();
	}

	public static RefundPaymentDto buildRefundPayment(){

		return RefundPaymentDto
				.builder()
				.orderId(1L)
				.reason("I want to refund the payment")
				.build();
	}

	public static RefundPaymentResponseDto buildRefundResponse(){

		return RefundPaymentResponseDto
				.builder()
				.orderId(1L)
				.transactionId("12T34H5")
				.status(TransactionStatus.APPROVED)
				.message("Successful approved")
				.build();
	}

}
