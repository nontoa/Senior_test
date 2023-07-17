package com.example.startupapp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.startupapp.configuration.MockConfiguration;
import com.example.startupapp.constants.AntifraudMockEndpoint;
import com.example.startupapp.dto.AntifraudRequestDto;
import com.example.startupapp.dto.AntifraudResponseDto;

/**
 * Antifraud Api Mock.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@FeignClient(name = "antifraudMock", url = "${antifraudMock.url}", configuration = MockConfiguration.class)
public interface AntifraudApiMock {

	@PostMapping(value = AntifraudMockEndpoint.ANTIFRAUD, consumes = MediaType.APPLICATION_JSON_VALUE)
	AntifraudResponseDto createAntifraudRequest(
			@RequestBody AntifraudRequestDto antifraudRequestDto);

}
