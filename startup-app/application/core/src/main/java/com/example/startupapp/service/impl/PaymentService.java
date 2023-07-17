package com.example.startupapp.service.impl;

import org.apache.commons.validator.routines.CreditCardValidator;
import com.example.startupapp.BankApiMock;
import com.example.startupapp.dao.Transaction;
import com.example.startupapp.mapper.CreateRefundMapper;
import com.example.startupapp.mapper.RefundResponseMapper;
import com.example.startupapp.repository.TransactionRepository;
import com.example.startupapp.repository.OrderRepository;
import com.example.startupapp.constants.OrderStatus;
import com.example.startupapp.constants.TransactionStatus;
import com.example.startupapp.dto.payments.CreatePaymentDto;
import com.example.startupapp.dto.payments.CreatePaymentResponseDto;
import com.example.startupapp.dto.payments.RefundPaymentDto;
import com.example.startupapp.dto.payments.RefundPaymentResponseDto;
import com.example.startupapp.exception.PaymentException;
import com.example.startupapp.mapper.CreatePaymentMapper;
import com.example.startupapp.mapper.PaymentResponseMapper;
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

		final var orderDao = ServiceUtils.buildOrderDaoToSave(OrderStatus.PENDING);
		var order = orderRepository.save(orderDao);
		final var transactionDao = ServiceUtils.buildTransactionDaoToSave(TransactionStatus.PENDING,
																		  null,
																		  order.getId(),
																		  createPaymentDto);
		transactionRepository.save(transactionDao);

		//Debe ir a antifraude, si no pasa actualizar orden y transaccion con el estado declinado por antifraude

		//Que pasa si el mock esta abajo y no responde, poner retry y si no actualizar la orden y tx con estado DECLINED no fue psible
		// contactar a la red
		final var createPaymentResponseBank =
				bankApiMock.createPayment(CreatePaymentMapper.mapCreatePayment(createPaymentDto, order.getId(), transactionDao.getId()));

		//Si el bank no responde poner la tx y la orden como declined

		orderRepository.save(ServiceUtils.updateOrderDao(orderDao, createPaymentResponseBank));
		transactionRepository.save(ServiceUtils.updateTransactionDao(transactionDao, createPaymentResponseBank));
		return PaymentResponseMapper.mapCreatePaymentResponse(createPaymentResponseBank, createPaymentDto, order.getId(),
															  transactionDao.getId());
	}

	@Override
	public RefundPaymentResponseDto refundPayment(final RefundPaymentDto refundPaymentDto)
			throws PaymentException {

		var orderDao = orderRepository.findById(refundPaymentDto.getOrderId());
		if (orderDao.isPresent()) {
			var order = orderDao.get();
			if (!order.getStatus().equals(OrderStatus.CAPTURED)) {
				throw new PaymentException(ERROR_STATUS, "Refund cannot be applied");
			}

			var parentTransaction = getParentTransaction(refundPaymentDto.getOrderId());

			final var transactionDao = ServiceUtils.buildTransactionDaoToSave(TransactionStatus.PENDING,
																			  null,
																			  refundPaymentDto,
																			  parentTransaction);
			transactionRepository.save(transactionDao);

			final var createPaymentResponseBank =
					bankApiMock.refundPayment(CreateRefundMapper.mapRefundPayment(refundPaymentDto));

			//Si el bank no responde poner la tx como Declined

			transactionRepository.save(ServiceUtils.updateTransactionDao(transactionDao, createPaymentResponseBank));

			order.setStatus(OrderStatus.REFUNDED);
			orderRepository.save(order);
			return RefundResponseMapper.mapCreateRefundResponse(createPaymentResponseBank, refundPaymentDto, transactionDao.getId());
		}
		throw new PaymentException(ERROR_STATUS, "The order does not exists");
	}

	private void validateInformation(CreatePaymentDto createPaymentDto) throws PaymentException {

		if (!cardValidator.isValid(createPaymentDto.getCard().getNumber())) {
			throw new PaymentException(ERROR_STATUS, "Invalid card number");
		}
	}

	private Transaction getParentTransaction(final Long orderId) throws PaymentException {

		var transactions = transactionRepository.findByOrderId(orderId);
		if (!transactions.isEmpty()) {
			return transactions.get(0);
		}
		throw new PaymentException(ERROR_STATUS, "There is not parent transaction");
	}

}
