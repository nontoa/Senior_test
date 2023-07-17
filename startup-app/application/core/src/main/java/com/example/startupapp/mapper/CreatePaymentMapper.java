package com.example.startupapp.mapper;

import com.example.startupapp.dao.TransactionDao;
import com.example.startupapp.dto.payments.CreatePaymentDto;
import com.example.startupapp.dto.CreatePaymentBankDto;

/**
 * Converts a {@link CreatePaymentDto} to {@link CreatePaymentBankDto}.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class CreatePaymentMapper {

	/**
	 * Constructor.
	 */
	private CreatePaymentMapper() {
		// Empty constructor.
	}

	/**
	 * Converts {@link CreatePaymentDto} to {@link CreatePaymentBankDto}
	 * @param createPaymentDto {@link CreatePaymentDto}
	 * @return {@link CreatePaymentBankDto}
	 */
	public static CreatePaymentBankDto mapCreatePayment (final CreatePaymentDto createPaymentDto,
														 final Long orderId,
														 final String transactionId){

		return CreatePaymentBankDto
				.builder()
				.orderId(orderId)
				.transactionId(transactionId)
				.paymentMethod(createPaymentDto.getPaymentMethod())
				.value(createPaymentDto.getValue())
				.currency(createPaymentDto.getCurrency())
				.cardHolderName(createPaymentDto.getCard().getHolder())
				.cardNumber(createPaymentDto.getCard().getNumber())
				.cardCsc(createPaymentDto.getCard().getCsc())
				.holderDocument(createPaymentDto.getCard().getDocument())
				.month(createPaymentDto.getCard().getExpiration().getMonth())
				.year(createPaymentDto.getCard().getExpiration().getYear())
				.build();
	}



}
