package com.example.startupapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.startupapp.AntifraudApiMock;
import com.example.startupapp.BankApiMock;
import com.example.startupapp.repository.OrderRepository;
import com.example.startupapp.repository.TransactionRepository;
import com.example.startupapp.service.IPaymentService;
import com.example.startupapp.service.IOperationService;
import com.example.startupapp.service.impl.PaymentService;
import com.example.startupapp.service.impl.OperationService;

/**
 * Configuration for the core module.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Configuration
public class CoreConfiguration {

	/**
	 * Creates a {@link PaymentService} instance.
	 *
	 * @return The instance created.
	 */
	@Bean
	public IPaymentService createPaymentService(final BankApiMock bankApiMock,
												final AntifraudApiMock antifraudApiMock,
												final TransactionRepository transactionRepository,
												final OrderRepository orderRepository) {

		return new PaymentService(bankApiMock,antifraudApiMock, transactionRepository, orderRepository);

	}

	/**
	 * Creates a {@link OperationService} instance.
	 *
	 * @return The instance created.
	 */
	@Bean
	public IOperationService createOperationService(final TransactionRepository transactionRepository,
													final OrderRepository orderRepository) {

		return new OperationService(transactionRepository, orderRepository);

	}

}
