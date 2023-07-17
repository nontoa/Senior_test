package com.example.startupapp.service.impl;

import org.apache.commons.validator.routines.CreditCardValidator;
import com.example.startupapp.BankApiMock;
import com.example.startupapp.cache.repository.TransactionRepository;
import com.example.startupapp.cache.repository.OrderRepository;
import com.example.startupapp.constants.OrderStatus;
import com.example.startupapp.constants.TransactionStatus;
import com.example.startupapp.dto.payments.CreatePaymentDto;
import com.example.startupapp.dto.payments.CreatePaymentResponseDto;
import com.example.startupapp.dto.payments.RefundPaymentDto;
import com.example.startupapp.dto.payments.RefundPaymentResponseDto;
import com.example.startupapp.exception.PaymentException;
import com.example.startupapp.mapper.CreatePaymentMapper;
import com.example.startupapp.mapper.PaymentResponseMapper;
import com.example.startupapp.mapper.CreateRefundMapper;
import com.example.startupapp.mapper.RefundResponseMapper;
import com.example.startupapp.service.IPaymentService;
import com.example.startupapp.utils.ServiceUtils;

/**
 * Implementation of {@link IPaymentService}.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class PaymentService implements IPaymentService {

	private static final String ERROR_STATUS = "Error";

	/**
	 * The PaymentsOS Rest Client.
	 */
	private final BankApiMock bankApiMock;

	private final CreditCardValidator cardValidator;

	/**
	 * Transaction repository to access to the database
	 */
	private final TransactionRepository transactionRepository;

	/**
	 * Order repository to access to the database
	 */
	private final OrderRepository orderRepository;

	public PaymentService(final BankApiMock bankApiMock,
						  final TransactionRepository transactionRepository,
						  final OrderRepository orderRepository) {

		this.bankApiMock = bankApiMock;
		this.transactionRepository = transactionRepository;
		this.orderRepository = orderRepository;
		this.cardValidator = new CreditCardValidator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CreatePaymentResponseDto createPayment(final CreatePaymentDto createPaymentDto) throws PaymentException {

		validateInformation(createPaymentDto);

		final var orderDao = ServiceUtils.buildOrderDaoToSave(OrderStatus.PENDING, createPaymentDto);
		final Long orderId = orderRepository.save(orderDao);
		final var transactionDao = ServiceUtils.buildTransactionDaoToSave(TransactionStatus.PENDING,
																		  null,
																		  orderId,
																		  createPaymentDto);
		transactionRepository.save(transactionDao);

		//Debe ir a antifraude, si no pasa actualizar orden y transaccion con el estado declinado por antifraude

		//Que pasa si el mock esta abajo y no responde, poner retry y si no actualizar la orden y tx con estado DECLINED no fue psible
		// contactar a la red
		final var createPaymentResponseBank =
				bankApiMock.createPayment(CreatePaymentMapper.mapCreatePayment(createPaymentDto, orderId, transactionDao.getId()));

		orderRepository.update(orderId, ServiceUtils.updateOrderDao(orderDao, createPaymentResponseBank));
		transactionRepository.update(ServiceUtils.updateTransactionDao(transactionDao, createPaymentResponseBank));
		return PaymentResponseMapper.mapCreatePaymentResponse(createPaymentResponseBank, createPaymentDto);
	}

	@Override
	public RefundPaymentResponseDto refundPayment(final RefundPaymentDto refundPaymentDto)
			throws PaymentException {

		var order = orderRepository.findById(refundPaymentDto.getOrderId());
		if (order != null) {
			if (!order.getStatus().equals(OrderStatus.CAPTURED)) {
				throw new PaymentException(ERROR_STATUS, "Refund cannot be applied");
			}

			final var transactionDao = ServiceUtils.buildTransactionDaoToSave(TransactionStatus.PENDING,
																			  null,
																			  refundPaymentDto,
																			  transaction);
			transactionRepository.save(transactionDao);

			final var createPaymentResponseBank =
					bankApiMock.refundPayment(CreateRefundMapper.mapRefundPayment(refundPaymentDto));

			transactionRepository.update(ServiceUtils.updateTransactionDao(transactionDao, createPaymentResponseBank));

			order.setStatus(OrderStatus.REFUNDED);
			orderRepository.update(order.getId(), order);
			return RefundResponseMapper.mapCreateRefundResponse(createPaymentResponseBank, refundPaymentDto);
		}
		throw new PaymentException(ERROR_STATUS, "The order does not exists");
	}

	private void validateInformation(CreatePaymentDto createPaymentDto) throws PaymentException {

		if (!cardValidator.isValid(createPaymentDto.getCard().getNumber())) {
			throw new PaymentException(ERROR_STATUS, "Invalid card number");
		}
	}

}
