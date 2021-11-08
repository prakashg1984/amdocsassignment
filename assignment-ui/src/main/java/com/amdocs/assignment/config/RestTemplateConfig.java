package com.amdocs.assignment.config;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Configuration
public class RestTemplateConfig {
	
	/**
	 * Create the custom RestTemplate bean
	 * @return
	 */
	@Bean
	public RestTemplateCustomizer restTemplateCustomizer() {
	    return restTemplate -> {
	        restTemplate.setRequestFactory(clientHttpRequestFactory());
	    };
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
	    SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
	    clientHttpRequestFactory.setConnectTimeout(300);
	    clientHttpRequestFactory.setReadTimeout(3000);
	    clientHttpRequestFactory.setBufferRequestBody(false);
	    return clientHttpRequestFactory;
	}
}
