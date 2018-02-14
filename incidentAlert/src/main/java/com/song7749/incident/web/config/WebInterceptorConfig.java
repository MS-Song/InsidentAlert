package com.song7749.incident.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * <pre>
 * Class Name : WebInterceptorConfig.java
 * Description : Spring MVC Interceptor Configure
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 14.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 14.
*/
@Configuration
public class WebInterceptorConfig extends WebMvcConfigurationSupport {

	@Autowired
	@Qualifier("logMessageInterceptorHandle")
	private HandlerInterceptor logMessageInterceptorHandle;

	/**
	 * Log Message Interceptor
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logMessageInterceptorHandle)
				.addPathPatterns("/**")
				.excludePathPatterns("/static/**");
	}

	/**
	 * swagger UI Resource
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");

	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");

	    registry.addResourceHandler("/static/**")
	      .addResourceLocations("classpath:/META-INF/resources/static/");
	}
}