package com.adrian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import feign.Contract;

@SpringBootApplication
@EnableFeignClients
//@EnableEurekaClient
public class ManchesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManchesterApplication.class, args);
	}

	@Bean
	public Contract feignContract() {
    	return new feign.Contract.Default();
	}
}

