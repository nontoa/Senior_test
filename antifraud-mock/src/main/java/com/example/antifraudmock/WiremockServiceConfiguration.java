package com.example.antifraudmock;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration class to mock services
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(MockServiceProperties.class)
public class WiremockServiceConfiguration {

	/**
	 * Create the MockServerStarter bean and start all mocks
	 *
	 * @param mockServiceProperties list of mock properties
	 * @return MockServerStarter
	 */
	@Bean
	public MockServerStarter createMocks(final MockServiceProperties mockServiceProperties) {

		final MockServerStarter mockServerStarter = new MockServerStarter(mockServiceProperties);
		mockServerStarter.startMocks();
		return mockServerStarter;
	}

}
