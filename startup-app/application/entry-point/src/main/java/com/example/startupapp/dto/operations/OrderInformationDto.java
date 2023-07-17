package com.example.startupapp.dto.operations;

import java.sql.Timestamp;
import java.util.List;

import com.example.startupapp.constants.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object to define the order information.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class OrderInformationDto {

	private String id;

	private List<String> transactions;

	private OrderStatus status;

	private Timestamp creationDate;

}
