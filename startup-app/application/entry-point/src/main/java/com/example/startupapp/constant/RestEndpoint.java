package com.example.startupapp.constant;

/**
 * Constant class to define REST endpoints
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class RestEndpoint {

	/**
	 * Constructor.
	 */
	private RestEndpoint() {
		// Empty constructor
	}

	/**
	 * Endpoint to create a payment
	 */
	public static final String PAYMENTS = "/payments";

	/**
	 * Endpoint to refund a payment
	 */
	public static final String REFUND = "/refund";

	/**
	 * Endpoint to get operations info
	 */
	public static final String OPERATIONS = "/operations";

	/**
	 * Endpoint to get transaction info
	 */
	public static final String TRANSACTIONS = "/transactions/{transactionId}";

	/**
	 * Endpoint to get order info
	 */
	public static final String ORDERS = "/orders/{orderId}";

}
