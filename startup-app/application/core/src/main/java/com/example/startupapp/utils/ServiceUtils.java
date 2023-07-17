package com.example.startupapp.utils;

import java.sql.Timestamp;
import java.util.UUID;

import com.example.startupapp.constants.OrderStatus;
import com.example.startupapp.constants.TransactionStatus;
import com.example.startupapp.constants.TransactionType;
import com.example.startupapp.dao.Order;
import com.example.startupapp.dao.Transaction;
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

	public static Transaction buildTransactionDaoToSave(final TransactionStatus transactionStatus,
														final String message,
														final Long orderId,
														final CreatePaymentDto createPaymentDto){

		return Transaction
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

	public static Transaction buildTransactionDaoToSave(final TransactionStatus transactionStatus,
														final String message,
														final RefundPaymentDto refundPaymentDto,
														final Transaction firstTransaction){

		return Transaction
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

	public static Order buildOrderDaoToSave(final OrderStatus orderStatus){

		return Order
				.builder()
				.status(orderStatus)
				.creationDate(new Timestamp(System.currentTimeMillis()))
				.build();
	}

	public static Order updateOrderDao(final Order orderDao,
									   final PaymentResponseBankDto bankResponse){

		orderDao.setStatus(bankResponse.getStatus().equals(TransactionStatus.APPROVED) ? OrderStatus.CAPTURED : OrderStatus.DECLINED);
		return orderDao;
	}

	public static Transaction updateTransactionDao(final Transaction transactionDao,
												   final PaymentResponseBankDto bankResponse){

		transactionDao.setStatus(bankResponse.getStatus());
		transactionDao.setMessage(bankResponse.getMessage());
		return transactionDao;
	}

	private static String generateTransactionId(){

		return UUID.randomUUID().toString();
	}

}
