package com.example.startupapp.configuration;

import static com.example.startupapp.utils.TestingUtils.getPrivateFieldValue;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertSame;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.example.startupapp.AntifraudApiMock;
import com.example.startupapp.BankApiMock;
import com.example.startupapp.repository.OrderRepository;
import com.example.startupapp.repository.TransactionRepository;

/**
 * Tests {@link CoreConfiguration}.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class CoreConfigurationTest {

	BankApiMock bankApiMock;
	AntifraudApiMock antifraudApiMock;
	TransactionRepository transactionRepository;
	OrderRepository orderRepository;

	CoreConfiguration configuration;

	@BeforeClass
	public void setUp(){
		bankApiMock = mock(BankApiMock.class);
		transactionRepository = mock(TransactionRepository.class);
		orderRepository = mock(OrderRepository.class);
		configuration = new CoreConfiguration();
	}

	@Test
	public void createPaymentServiceTest() throws NoSuchFieldException, IllegalAccessException {

		var paymentService = configuration.createPaymentService(bankApiMock,
																antifraudApiMock,
																transactionRepository,
																orderRepository);

		assertSame(getPrivateFieldValue(paymentService, "bankApiMock"), bankApiMock);
		assertSame(getPrivateFieldValue(paymentService, "antifraudApiMock"), antifraudApiMock);
		assertSame(getPrivateFieldValue(paymentService, "transactionRepository"), transactionRepository);
		assertSame(getPrivateFieldValue(paymentService, "orderRepository"), orderRepository);
	}

}
