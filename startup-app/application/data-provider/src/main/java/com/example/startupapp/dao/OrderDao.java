package com.example.startupapp.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.example.startupapp.constants.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data access object for orders
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class OrderDao implements Serializable {

	private Long id;

	private OrderStatus status;

	private Timestamp creationDate;


}
