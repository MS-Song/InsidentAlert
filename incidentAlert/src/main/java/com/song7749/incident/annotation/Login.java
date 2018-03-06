package com.song7749.incident.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.song7749.incident.drs.type.AuthType;
import com.song7749.incident.drs.type.LoginResponseType;


@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
	AuthType[] value() default AuthType.NORMAL;
	LoginResponseType type() default LoginResponseType.EXCEPTION;
}