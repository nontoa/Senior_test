package com.example.antifraudmock;

import static java.util.Objects.nonNull;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;

/**
 * The server to start the mocks services
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class MockServerStarter {

	private static final String SPLIT_MARK = "!";

	/**
	 * The LOGGER of the class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MockServerStarter.class);

	/**
	 * list of mock services with configuration
	 */
	private final MockServiceProperties mockServiceProperties;

	/**
	 * Constructor class
	 */
	public MockServerStarter(final MockServiceProperties mockServiceProperties) {

		this.mockServiceProperties = mockServiceProperties;
	}

	/**
	 * Process the list of mock services and start all
	 */
	public void startMocks() {

		if (nonNull(mockServiceProperties)) {
			mockServiceProperties.getServices().forEach(this::startMockService);
		}
	}

	/**
	 * If the mock service is enabled start it
	 *
	 * @param nameService    name of service to create
	 * @param mockProperties properties of service to create
	 */
	private void startMockService(final String nameService, final MockServiceProperties.MockProperties mockProperties) {

		LOGGER.info("Creating mock: [{}] to see the request go to: {}://localhost:{}/__admin/",
					nameService, mockProperties.isSecurePort() ? "https" : "http", mockProperties.getPort());
		new WireMockServer(mockProperties.isSecurePort() ?
						   buildSecurePortMockProperties(mockProperties) : buildMockProperties(mockProperties)).start();

	}

	/**
	 * Create a WireMockConfiguration with the MockProperties values
	 *
	 * @param mockProperties object with values to config
	 * @return WireMockConfiguration
	 */
	private WireMockConfiguration buildMockProperties(final MockServiceProperties.MockProperties mockProperties) {

		return WireMockConfiguration.options()
									.usingFilesUnderClasspath(mockProperties.getResourcesPath())
									.fileSource(new ClasspathFileSource(getPathResources(mockProperties.getResourcesPath())))
									.asynchronousResponseEnabled(true)
									.extensions(new ResponseTemplateTransformer(true))
									.port(mockProperties.getPort());
	}

	/**
	 * Create a WireMockConfiguration with the MockProperties values and httpsPort
	 *
	 * @param mockProperties object with values to config
	 * @return WireMockConfiguration
	 */
	private WireMockConfiguration buildSecurePortMockProperties(final MockServiceProperties.MockProperties mockProperties) {

		return WireMockConfiguration.options()
									.usingFilesUnderClasspath(mockProperties.getResourcesPath())
									.fileSource(new ClasspathFileSource(getPathResources(mockProperties.getResourcesPath())))
									.asynchronousResponseEnabled(true)
									.extensions(new ResponseTemplateTransformer(true))
									.httpsPort(mockProperties.getPort())
									.httpDisabled(true);
	}

	/**
	 * This method allows to get the path of the wiremock resources, allowing the jar at spring boot to find the resource folder more
	 * specifically the wiremock folder resources
	 *
	 * @return Path Resources Wiremock
	 */
	private String getPathResources(final String folderMock) {

		final URL url = getClass().getClassLoader().getResource(folderMock);
		String pathStubs = folderMock;

		if (nonNull(url)) {

			final String[] arrayPartitionPath = url.toExternalForm().split(SPLIT_MARK);

			if (arrayPartitionPath.length == 3) {

				final String pathResourcesMock = arrayPartitionPath[1] + arrayPartitionPath[2];
				pathStubs = (pathResourcesMock).substring(1);
			}
		}

		return pathStubs;
	}

}
