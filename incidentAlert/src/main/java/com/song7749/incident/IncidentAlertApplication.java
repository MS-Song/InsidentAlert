package com.song7749.incident;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages= {"com.song7749"})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
public class IncidentAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidentAlertApplication.class, args);
	}
}