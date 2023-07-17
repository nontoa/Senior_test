package com.example.startupapp.dao;

import java.io.Serializable;
import java.sql.Timestamp;

import com.example.startupapp.constants.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data access object for orders
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Entity
@Table (name = "order_information")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column
	private Timestamp creationDate;


}
