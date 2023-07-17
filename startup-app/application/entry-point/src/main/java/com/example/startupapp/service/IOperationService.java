package com.example.startupapp.service;

import com.example.startupapp.dto.operations.OrderInformationDto;
import com.example.startupapp.dto.operations.TransactionInformationDto;
import com.example.startupapp.exception.PaymentException;

/**
 * Service to get operation information
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public interface IOperationService {

	/**
	 * Gets a transaction information
	 * @param transactionId Transaction id
	 * @return Transaction information
	 */
	TransactionInformationDto getTransactionInformation(final String transactionId) throws PaymentException;

	/**
	 * Gets an order information
	 * @param orderId Order id
	 * @return Order information
	 */
	OrderInformationDto getOrderInformation(final Long orderId) throws PaymentException;

}
