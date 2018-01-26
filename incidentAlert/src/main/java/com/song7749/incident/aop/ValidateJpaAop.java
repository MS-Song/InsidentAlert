package com.song7749.incident.aop;

import static com.song7749.util.LogMessageFormatter.format;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.song7749.incident.validate.Validate;
import com.song7749.incident.validate.ValidateGroupBase;

@Component
@Aspect
public class ValidateJpaAop {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * validator 설정
	 */
	protected Validator validatorFactoryBean= new LocalValidatorFactoryBean();

    //@Pointcut("execution(* com.song7749.*.*.*(..))")
	//@Pointcut("@within(com.song7749.util.validate.Validate) ")
	//@Pointcut("@target(com.song7749.util.validate.Validate)")
	//@Pointcut("@annotation(com.song7749.util.validate.Validate)")
	// TODO Annotation 기반으로 PointCut 동작 되도록 패치 준비
    @Pointcut("this(org.springframework.data.repository.Repository)")
    public void hasAnnotationValidate() {}

	@Around("hasAnnotationValidate()")
	public Object validateJpaAop(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method invocation = signature.getMethod();

	    // has parameter
		if(null!=invocation.getParameters()
				&& invocation.getParameters().length>0){

			// validate write logger
			if(logger.isTraceEnabled()){
				Object[] log = {
						invocation.getName()
						,String.valueOf(invocation.getParameterCount())
				};
				logger.trace(format("mehtod : {}\nparamsize:{}", "Validate AOP"),log);
			}


			Validate validate=null;
			// interface has annotation
			if(invocation.isAnnotationPresent(Validate.class)){
				validate = invocation.getAnnotation(Validate.class);

				// validate group
				Class<? extends ValidateGroupBase>[] baseList=validate.VG();
				// property has
				String[] properties = validate.property();

				// start validate
				for(Object o:joinPoint.getArgs()){
					// parameter is not null and object us null
					if(validate.nullable() == false && o == null){
						throw new IllegalArgumentException(" parameter is not null");
					}

					Set<ConstraintViolation<Object>> cv= null;

					// has property
					if(null!=properties && properties.length>0){

						Set<ConstraintViolation<Object>> propertySet=null;

						for(String property : properties){
							// 프로퍼티 값이 존재하면..
							if(null!=property && ""!=property){
								if(null==baseList){
									propertySet=validatorFactoryBean.validateProperty(o, property);
								} else{
									propertySet=validatorFactoryBean.validateProperty(o, property, baseList);
								}
							}
							// validate 결과가 존재하면..
							if(null!=propertySet
									&& propertySet.size()>0){
								if(cv==null){
									cv=propertySet;
								} else{
									cv.addAll(propertySet);
								}
							}
						}
					} else{ // not has property
						if(null==baseList){
							cv=validatorFactoryBean.validate(o);
						} else {
							cv=validatorFactoryBean.validate(o,baseList);
						}
					}
					logger.trace(format("{}", "Log Message"),cv);
					if(null!=cv && cv.size()>0){
						for(ConstraintViolation<?> c:cv){
							// 프록시 객체에서 발생한 에러를 건너뛴
							if(c.getRootBeanClass().getName().indexOf("_$$_javassist_")==-1){
								logger.trace(format("{} , 입력값 : {}","Validate Exception"),c.getPropertyPath() + " 은(는) " + c.getMessage(),c.getInvalidValue());
								throw new IllegalArgumentException(c.getPropertyPath() + "= 은(는) " + c.getMessage());
							}
						}
					}
				}
			}
		}

		try {
			return joinPoint.proceed();
		} catch (javax.validation.ConstraintViolationException e) {
			throw new IllegalArgumentException(e.getConstraintViolations().iterator().next().getPropertyPath() + " 은(는) " + e.getConstraintViolations().iterator().next().getMessage());
		}
	}
}