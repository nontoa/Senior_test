package com.example.startupapp.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.startupapp.dao.OrderDao;
import com.example.startupapp.dao.TransactionDao;
import com.example.startupapp.dto.operations.OrderInformationDto;

/**
 * Builds a {@link OrderInformationDto}.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class OrderInformationMapper {

	/**
	 * Constructor.
	 */
	private OrderInformationMapper() {
		// Empty constructor.
	}

	/**
	 * Builds a {@link OrderInformationDto}
	 * @param order {@link OrderDao}
	 * @return {@link OrderInformationDto}
	 */
	public static OrderInformationDto mapOrderInformation(final OrderDao order){

		List<String> transactions = new ArrayList<>();
		for(TransactionDao transaction : order.getTransactions()){
			transactions.add(transaction.getId());
		}
		var orderDto = OrderInformationDto
				.builder()
				.id(order.getId())
				.transactions(transactions)
				.status(order.getStatus())
				.creationDate(order.getCreationDate())
				.build();

		return orderDto;
	}

}
