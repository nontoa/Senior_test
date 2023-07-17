package com.example.startupapp.mapper;

import com.example.startupapp.dao.Order;
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
	 * @param order {@link Order}
	 * @return {@link OrderInformationDto}
	 */
	public static OrderInformationDto mapOrderInformation(final Order order){

		var orderDto = OrderInformationDto
				.builder()
				.id(order.getId())
				.status(order.getStatus())
				.creationDate(order.getCreationDate())
				.build();

		return orderDto;
	}

}
