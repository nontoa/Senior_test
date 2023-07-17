package com.example.startupapp.constants;

/**
 * Constant class to define REST endpoints to the bank mock
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class BankMockEndpoint {

	/**
	 * Constructor.
	 */
	private BankMockEndpoint() {
		// Empty constructor
	}

	/**
	 * Endpoint to create a payment
	 */
	public static final String PAYMENTS = "/payments";

	/**
	 * Endpoint to refund a payment
	 */
	public static final String REFUND = PAYMENTS + "/refund";

}
