package com.song7749.incident.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
//@EnableWebMvc
//@ComponentScan("com.song7749")
public class HttpInterceptorConfig extends WebMvcConfigurationSupport {

	@Autowired
	@Qualifier("logMessageInterceptorHandle")
	private HandlerInterceptor logMessageInterceptorHandle;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logMessageInterceptorHandle)
				.addPathPatterns("/**")
				.excludePathPatterns("/static/**");
	}
}
