package com.song7749.incident.web.config;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.song7749.base.MessageVo;

/**
 * <pre>
 * Class Name : GlobalResponseAdvice.java
 * Description : Response 의 wrapper object 를 추가 한다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 8.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 8.
*/

@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

		Logger logger = LoggerFactory.getLogger(getClass());

		@Override
		public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
			return true;
		}

		@Override
		public Object beforeBodyWrite(
			Object body,
			MethodParameter returnType,
			MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request,
			ServerHttpResponse response) {

			logger.trace(format("{}", "ResponseBodyAdvice"),body);

			int rowCount = 1;
			if(body == null) {
				rowCount = 0;
			} else  if(body instanceof Collection) {
				rowCount = ((Collection<?>) body).size();
			}
			// exception 이 발생한 경우 status code 를 변경한다
			// 이미 MessageVo 로 변경된 경우 exception 이 발생한 경우 이다.
			if(body instanceof MessageVo) {
				// response code 변경
				response.setStatusCode(HttpStatus.valueOf(((MessageVo)body).getHttpStatus()));
				return body;
			} else {
				return new MessageVo(HttpServletResponse.SC_OK, rowCount, body);
			}
		}
}