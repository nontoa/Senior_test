package com.example.startupapp.service.impl;

import org.modelmapper.ModelMapper;
import com.example.startupapp.cache.repository.OrderRepository;
import com.example.startupapp.cache.repository.TransactionRepository;
import com.example.startupapp.dto.operations.OrderInformationDto;
import com.example.startupapp.dto.operations.TransactionInformationDto;
import com.example.startupapp.exception.PaymentException;
import com.example.startupapp.mapper.OrderInformationMapper;
import com.example.startupapp.service.IOperationService;

/**
 * Implementation of {@link IOperationService}.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class OperationService implements IOperationService {

	private static final String ERROR_STATUS = "Error";

	/**
	 * Transaction repository to access to the database
	 */
	private final TransactionRepository transactionRepository;

	/**
	 * Order repository to access to the database
	 */
	private final OrderRepository orderRepository;

	public OperationService(final TransactionRepository transactionRepository,
							final OrderRepository orderRepository) {

		this.transactionRepository = transactionRepository;
		this.orderRepository = orderRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TransactionInformationDto getTransactionInformation(final String transactionId) throws PaymentException {
		final var transaction = transactionRepository.findById(transactionId);
		if (transaction != null){
			return new ModelMapper().map(transaction, TransactionInformationDto.class);
		}
		throw new PaymentException(ERROR_STATUS, "The transaction id is not registered");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderInformationDto getOrderInformation(final Long orderId) throws PaymentException {
		final var order = orderRepository.findById(orderId);
		if (order != null){
			return OrderInformationMapper.mapOrderInformation(order);
		}
		throw new PaymentException(ERROR_STATUS, "The order id is not registered");
	}
}
