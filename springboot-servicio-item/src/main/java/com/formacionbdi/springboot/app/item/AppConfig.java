package com.formacionbdi.springboot.app.item;

import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.core.util.Duration;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {
	
	@Bean("clienteRest")
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id)
					.circuitBreakerConfig(CircuitBreakerConfig.custom()
					.slidingWindowSize(10)
					.failureRateThreshold(150)
					.waitDurationInOpenState(java.time.Duration.ofSeconds(10L))
					.build())
					.timeLimiterConfig(TimeLimiterConfig.ofDefaults())
					.build();
		});
	}

}
