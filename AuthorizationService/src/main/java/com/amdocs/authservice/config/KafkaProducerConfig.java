package com.amdocs.authservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
public class KafkaProducerConfig {
    static Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

	@Value("${kafka.bootstrap.servers}")
	private String bootstrapServers;
	@Value("${kafka.producer.acks:all}")
	private String acks;
	@Value("${kafka.producer.retries:0}")
	private int retries;
	
	/**
	 * Create a Kafka Producer Factory Bean
	 * @return
	 */
	@Bean
	public ProducerFactory<String, Map> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	
	/**
	 * Properties for Kafka Producer Config
	 * @return
	 */
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.ACKS_CONFIG, acks);
		props.put(ProducerConfig.RETRIES_CONFIG, retries);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}
	
	/**
	 * Create the KafkaTemplate bean
	 * @return
	 */
	@Bean(name = "RouteEventProducerTemplate")
	public KafkaTemplate<String, Map> kafkaTemplate() {
		return new KafkaTemplate<String, Map>(producerFactory());
	}
}
