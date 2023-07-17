package com.example.startupapp.service.impl;

import java.rmi.UnexpectedException;
import java.util.Objects;

import org.apache.commons.validator.routines.CreditCardValidator;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;
import com.example.startupapp.AntifraudApiMock;
import com.example.startupapp.BankApiMock;
import com.example.startupapp.constants.AntifraudStatus;
import com.example.startupapp.constants.TransactionStatus;
import com.example.startupapp.dao.Order;
import com.example.startupapp.dao.Transaction;
import com.example.startupapp.dto.AntifraudRequestDto;
import com.example.startupapp.dto.AntifraudResponseDto;
import com.example.startupapp.dto.CreatePaymentBankDto;
import com.example.startupapp.dto.PaymentResponseBankDto;
import com.example.startupapp.dto.RefundPaymentBankDto;
import com.example.startupapp.mapper.AntifraudRequestMapper;
import com.example.startupapp.mapper.CreateRefundMapper;
import com.example.startupapp.mapper.RefundResponseMapper;
import com.example.startupapp.repository.TransactionRepository;
import com.example.startupapp.repository.OrderRepository;
import com.example.startupapp.constants.OrderStatus;
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

	private final BankApiMock bankApiMock;

	private final AntifraudApiMock antifraudApiMock;

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
						  final AntifraudApiMock antifraudApiMock,
						  final TransactionRepository transactionRepository,
						  final OrderRepository orderRepository) {

		this.bankApiMock = bankApiMock;
		this.antifraudApiMock = antifraudApiMock;
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

		var order = createOrder();
		var transaction = createTransaction(order.getId(), createPaymentDto);
		var antifraudResponse =
				callAntifraudService(AntifraudRequestMapper.mapAntifraudRequest(createPaymentDto.getCard()), transaction, order);
		transaction = ServiceUtils.updateTransactionDao(transaction, antifraudResponse);
		order = ServiceUtils.updateOrderDao(order, antifraudResponse);
		updateInformation(transaction, order);
		if (antifraudResponse.getStatus().equals(AntifraudStatus.FRAUD)) {
			return PaymentResponseMapper.mapCreatePaymentResponse(transaction);
		}
		final var paymentResponseBank =
				callBankService(CreatePaymentMapper.mapCreatePayment(createPaymentDto, order.getId(), transaction.getId()), transaction,
								order);
		transaction = ServiceUtils.updateTransactionDao(transaction, paymentResponseBank);
		order = ServiceUtils.updateOrderDao(order, paymentResponseBank);
		updateInformation(transaction, order);
		return PaymentResponseMapper.mapCreatePaymentResponse(transaction);
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
			var transaction = ServiceUtils.buildTransactionDaoToSave(refundPaymentDto, parentTransaction);
			updateInformation(transaction, null);
			final var paymentResponseBank =
					callBankService(CreateRefundMapper.mapRefundPayment(refundPaymentDto), transaction, order);
			transaction = ServiceUtils.updateTransactionDao(transaction, paymentResponseBank);
			order = ServiceUtils.updateOrderDaoRefund(order, paymentResponseBank);
			updateInformation(transaction, order);
			return RefundResponseMapper.mapCreateRefundResponse(transaction);
		}
		throw new PaymentException(ERROR_STATUS, "The order does not exists");
	}

	@Retryable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.backoffDelay}"))
	private AntifraudResponseDto callAntifraudService(final AntifraudRequestDto antifraudRequestDto, Transaction transaction, Order order) {

		try {
			final var antifraudResponse = antifraudApiMock.createAntifraudRequest(antifraudRequestDto);
			return antifraudResponse;
		} catch (Exception e) {
			handleAntifraudServiceException(transaction, order);
			throw new RuntimeException("Cannot establish connection with antifraud service");
		}
	}

	@Recover
	public void handleAntifraudServiceException(Transaction transaction, Order order) {

		transaction.setStatus(TransactionStatus.DECLINED);
		transaction.setMessage("There was a connection error with antifraud service");
		order.setStatus(OrderStatus.DECLINED);
		updateInformation(transaction, order);
	}

	@Retryable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.backoffDelay}"))
	private PaymentResponseBankDto callBankService(final Object requestBankDto, Transaction transaction, Order order) {

		try {
			if (requestBankDto instanceof CreatePaymentBankDto) {
				final var paymentBankResponse = bankApiMock.createPayment((CreatePaymentBankDto) requestBankDto);
				return paymentBankResponse;
			} else if (requestBankDto instanceof RefundPaymentBankDto) {
				final var paymentBankResponse = bankApiMock.refundPayment((RefundPaymentBankDto) requestBankDto);
				return paymentBankResponse;
			} else {
				throw new UnexpectedException("Object cannot be casted");
			}

		} catch (Exception e) {
			handleBankServiceException(transaction, order);
			throw new RuntimeException("Cannot establish connection with bank service", e);
		}
	}

	@Recover
	public void handleBankServiceException(Transaction transaction, Order order) {

		transaction.setStatus(TransactionStatus.DECLINED);
		transaction.setMessage("There was a connection error with bank service");
		order.setStatus(OrderStatus.DECLINED);
		updateInformation(transaction, order);
	}

	@Retryable(maxAttemptsExpression = "${retry.database.maxAttempts}",
			backoff = @Backoff(delayExpression = "${retry.database.backoffDelay}"))
	@Transactional
	public void updateInformation(Transaction transaction, Order order) {

		try {
			if (Objects.nonNull(transaction)) {
				transactionRepository.save(transaction);
			}
			if (Objects.nonNull(order)) {
				orderRepository.save(order);
			}
		} catch (Exception e) {
			throw new RuntimeException("Cannot establish connection with the database", e);
		}
	}

	@Retryable(maxAttemptsExpression = "${retry.database.maxAttempts}",
			backoff = @Backoff(delayExpression = "${retry.database.backoffDelay}"))
	@Transactional
	public Order createOrder() {

		try {
			var orderDao = ServiceUtils.buildOrderDaoToSave(OrderStatus.PENDING);
			return orderRepository.save(orderDao);
		} catch (Exception e) {
			throw new RuntimeException("Cannot establish connection with the database", e);
		}
	}

	@Retryable(maxAttemptsExpression = "${retry.database.maxAttempts}",
			backoff = @Backoff(delayExpression = "${retry.database.backoffDelay}"))
	@Transactional
	public Transaction createTransaction(final Long orderId, final CreatePaymentDto createPaymentDto) {

		try {
			var transaction = ServiceUtils.buildInitialTransactionDaoToSave(orderId, createPaymentDto);
			return transactionRepository.save(transaction);
		} catch (Exception e) {
			throw new RuntimeException("Cannot establish connection with the database", e);
		}
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
