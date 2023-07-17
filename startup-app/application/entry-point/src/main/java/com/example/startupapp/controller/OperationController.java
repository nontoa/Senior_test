package com.example.startupapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.startupapp.constant.RestEndpoint;
import com.example.startupapp.dto.operations.OrderInformationDto;
import com.example.startupapp.dto.operations.TransactionInformationDto;
import com.example.startupapp.exception.PaymentException;
import com.example.startupapp.service.IOperationService;

/**
 * Controller for <code>/get-transactions</code> resource endpoint
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@RestController
@RequestMapping(RestEndpoint.OPERATIONS)
public class OperationController {

	/**
	 * Operation Service
	 */
	private IOperationService operationService;

	/**
	 * PaymentController's constructor
	 * @param operationService Operation Service
	 */
	public OperationController(final IOperationService operationService){
		this.operationService = operationService;
	}

	/**
	 * Gets a transaction information
	 * @param transactionId Transaction id
	 * @return Transaction's information
	 * @throws PaymentException
	 */
	@GetMapping(value = RestEndpoint.TRANSACTIONS)
	public ResponseEntity<TransactionInformationDto> getTransactionInformation(@PathVariable String transactionId) throws PaymentException {

		return new ResponseEntity<>(operationService.getTransactionInformation(transactionId),
									HttpStatus.OK);
	}

	/**
	 * Gets an order information
	 * @param orderId Order id
	 * @return Order's information
	 * @throws PaymentException
	 */
	@GetMapping(value = RestEndpoint.ORDERS)
	public ResponseEntity<OrderInformationDto> getOrderInformation(@PathVariable Long orderId) throws PaymentException {

		return new ResponseEntity<>(operationService.getOrderInformation(orderId),
									HttpStatus.OK);
	}

}
