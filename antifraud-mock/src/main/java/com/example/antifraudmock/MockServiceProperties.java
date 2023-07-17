package com.example.antifraudmock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration list mocks services
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@ConfigurationProperties(prefix="mock")
public class MockServiceProperties {

	/**
	 * The map that contains the configuration of the list mock services
	 */
	private Map<String, MockProperties> services = new HashMap<>();

	/**
	 * Returns services
	 */
	public Map<String, MockProperties> getServices() {

		return services;
	}

	/**
	 * Configuration properties for mocks services
	 */
	public static class MockProperties {


		/**
		 * Port where the mock service will be listened
		 */
		private int port;

		/**
		 * Indicates if the port where the mock service will be deployed is secure
		 */
		private boolean securePort;

		/**
		 * Path where the mock will go to find the resources
		 * of the requests and responses
		 */
		private String resourcesPath;

		public int getPort() {

			return port;
		}

		public void setPort(int port) {

			this.port = port;
		}

		public String getResourcesPath() {

			return resourcesPath;
		}

		public void setResourcesPath(String resourcesPath) {

			this.resourcesPath = resourcesPath;
		}

		public boolean isSecurePort() {
			return securePort;
		}

		public void setSecurePort(boolean securePort) {
			this.securePort = securePort;
		}
	}

}
