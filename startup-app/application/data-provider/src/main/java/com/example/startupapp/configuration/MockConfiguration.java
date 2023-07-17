package com.example.startupapp.configuration;

import java.util.Collections;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import feign.codec.Decoder;
import feign.codec.Encoder;

/**
 * Configures feign to be used as Mock.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class MockConfiguration {

	@Bean
	public Decoder feignDecoder() {
		final var jacksonConverter =  new MappingJackson2HttpMessageConverter();
		jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		final ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
		return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
	}
	@Bean
	public Encoder feignEncoder(){
		final var jacksonConverter =  new MappingJackson2HttpMessageConverter();
		jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		final ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
		return new SpringEncoder(objectFactory);
	}

}
