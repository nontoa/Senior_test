package com.example.startupapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.example.startupapp.exception.PaymentException;
import com.example.startupapp.service.IPaymentService;
import com.example.startupapp.utils.TestUtils;

/**
 * Tests {@link PaymentController}.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class PaymentControllerTest {

	private static final String ERROR = "Error";
	private static final String ERROR_EXISTING_TRANSACTION = "The transaction id already exists";

	IPaymentService paymentServiceToTest;

	PaymentController paymentController;

	@BeforeClass
	public void setUp() {
		paymentServiceToTest = mock(IPaymentService.class);
		paymentController = new PaymentController(paymentServiceToTest);
	}

	@Test
	public void createPaymentTest() throws PaymentException {

		reset(paymentServiceToTest);

		when(paymentServiceToTest.createPayment(any())).thenReturn(TestUtils.buildPaymentResponse());

		final var response =
				paymentController.createPayment(TestUtils.buildCreatePayment());

		verify(paymentServiceToTest, times(1)).createPayment(any());

		assertNotNull(response);

	}

	@Test
	public void refundPaymentTest() throws PaymentException {

		reset(paymentServiceToTest);

		when(paymentServiceToTest.refundPayment(any())).thenReturn(TestUtils.buildRefundResponse());

		final var response =
				paymentController.refundPayment(TestUtils.buildRefundPayment());

		verify(paymentServiceToTest, times(1))
				.refundPayment(any());

		assertNotNull(response);

	}

	@Test
	public void createPaymentTestWithExistingTransaction() throws PaymentException {

		reset(paymentServiceToTest);

		when(paymentServiceToTest.createPayment(any())).thenThrow(new PaymentException(ERROR,ERROR_EXISTING_TRANSACTION));

		try{
			final var response =
					paymentController.createPayment(TestUtils.buildCreatePayment());
		}catch(PaymentException ex){

			assertEquals(ex.getStatus(),ERROR);
			assertEquals(ex.getMessage(),ERROR_EXISTING_TRANSACTION);
		}
		verify(paymentServiceToTest, times(1)).createPayment(any());

	}

}
