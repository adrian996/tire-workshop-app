package com.adrian.london;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.Contract;


//@ImportAutoConfiguration({FeignAutoConfiguration.class})
@SpringBootApplication
@EnableFeignClients
//@EnableEurekaClient
public class LondonApplication {

	public static void main(String[] args) {
		SpringApplication.run(LondonApplication.class, args);
	}

	@Bean
	public Contract feignContract() {
    	return new feign.Contract.Default();
	}
}

