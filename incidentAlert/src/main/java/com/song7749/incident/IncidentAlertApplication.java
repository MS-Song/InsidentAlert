package com.song7749.incident;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication(scanBasePackages = { "com.song7749" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
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
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setPropertyCondition(Conditions.isNotNull())			// null 인 필드는 제거
				.setSourceNamingConvention(NamingConventions.NONE)		// 소스측 필드 네이밍 설정 초기화
				.setDestinationNamingConvention(NamingConventions.NONE)	// 타겟측 필드 네이밍 설정 총기화
				.setMatchingStrategy(MatchingStrategies.STRICT)			// 정확한 명칭 매핑만 허용
				;
		return modelMapper;
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	public static void main(String[] args) {
		SpringApplication.run(IncidentAlertApplication.class, args);
	}
}