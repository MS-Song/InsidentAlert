package com.song7749.incident;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@SpringBootApplication(scanBasePackages= {"com.song7749"})
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableTransactionManagement
@EnableJpaRepositories
@EnableCaching
public class IncidentAlertApplication {

	@PostConstruct
    void started() {
      TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

	@Bean
	public ModelMapper modelMapper() {
	  return new ModelMapper();
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	public static void main(String[] args) {
		SpringApplication.run(IncidentAlertApplication.class, args);
	}
}