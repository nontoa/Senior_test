package com.example.startupapp.utils;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

import org.aspectj.weaver.ast.Or;
import com.example.startupapp.constants.OrderStatus;
import com.example.startupapp.constants.TransactionStatus;
import com.example.startupapp.constants.TransactionType;
import com.example.startupapp.dao.OrderDao;
import com.example.startupapp.dao.TransactionDao;
import com.example.startupapp.dto.payments.CreatePaymentDto;
import com.example.startupapp.dto.PaymentResponseBankDto;
import com.example.startupapp.dto.payments.RefundPaymentDto;

/**
 * Utils class to build object to be saved on the database
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class ServiceUtils {

	public static TransactionDao buildTransactionDaoToSave(final TransactionStatus transactionStatus,
														   final String message,
														   final Long orderId,
														   final CreatePaymentDto createPaymentDto){

		return TransactionDao
				.builder()
				.id(generateTransactionId())
				.orderId(orderId)
				.type(TransactionType.AUTHORIZATION_CAPTURE)
				.paymentMethod(createPaymentDto.getPaymentMethod())
				.merchantName(createPaymentDto.getMerchantName())
				.value(createPaymentDto.getValue())
				.currency(createPaymentDto.getCurrency())
				.buyerName(createPaymentDto.getBuyer().getFirstName()+createPaymentDto.getBuyer().getLastName())
				.buyerDocument(createPaymentDto.getBuyer().getDocument())
				.status(transactionStatus)
				.message(message)
				.creationDate(new Timestamp(System.currentTimeMillis()))
				.build();
	}

	public static TransactionDao buildTransactionDaoToSave(final TransactionStatus transactionStatus,
														   final String message,
														   final RefundPaymentDto refundPaymentDto,
														   final TransactionDao firstTransaction){

		return TransactionDao
				.builder()
				.id(generateTransactionId())
				.orderId(refundPaymentDto.getOrderId())
				.type(TransactionType.REFUND)
				.paymentMethod(firstTransaction.getPaymentMethod())
				.merchantName(firstTransaction.getMerchantName())
				.value(firstTransaction.getValue())
				.currency(firstTransaction.getCurrency())
				.buyerName(firstTransaction.getBuyerName())
				.buyerDocument(firstTransaction.getBuyerDocument())
				.status(transactionStatus)
				.message(message)
				.creationDate(new Timestamp(System.currentTimeMillis()))
				.build();
	}

	public static OrderDao buildOrderDaoToSave(final OrderStatus orderStatus,
										 final CreatePaymentDto createPaymentDto){

		return OrderDao
				.builder()
				.status(orderStatus)
				.creationDate(new Timestamp(System.currentTimeMillis()))
				.build();
	}

	public static OrderDao updateOrderDao(final OrderDao orderDao,
										  final PaymentResponseBankDto bankResponse){

		orderDao.setStatus(bankResponse.getStatus().equals(TransactionStatus.APPROVED) ? OrderStatus.CAPTURED : OrderStatus.DECLINED);
		return orderDao;
	}

	public static TransactionDao updateTransactionDao(final TransactionDao transactionDao,
													  final PaymentResponseBankDto bankResponse){

		transactionDao.setStatus(bankResponse.getStatus());
		transactionDao.setMessage(bankResponse.getMessage());
		return transactionDao;
	}

	private static String generateTransactionId(){

		return UUID.randomUUID().toString();
	}

}
