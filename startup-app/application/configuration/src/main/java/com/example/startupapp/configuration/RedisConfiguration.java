package com.example.startupapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Configuration for Redis
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
@Configuration
public class RedisConfiguration {

	/**
	 * Indicates the redis IP.
	 */
	@Value("${redis.host}")
	private String redisHost;

	/**
	 * Indicates the redis Port.
	 */
	@Value("${redis.port}")
	private Integer redisPort;


	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new
				RedisStandaloneConfiguration(redisHost, redisPort);
		return  new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template .setConnectionFactory(jedisConnectionFactory());
		return template;
	}

}
