package com.fiestacabin.model.validator;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.inject.Injector;

public class Validators {
	private static final Logger LOG = LoggerFactory.getLogger(Validators.class);

	private Injector injector;
	private Map<Class<?>, Class<?>> validatorClasses;
	private Map<Class<?>, Method> validatorMethods;

	@Inject
	public Validators(ValidatorConfiguration config, Injector injector) {
		this.injector = injector;
		this.validatorClasses = Maps.newHashMap();
		this.validatorMethods = Maps.newHashMap();

		Reflections reflections = new Reflections(config.getBasePackage(),
				new MethodAnnotationsScanner());
		Set<Method> vMethods = reflections.getMethodsAnnotatedWith(Validates.class);

		for (Method m : vMethods) {
			Validates validates = m.getAnnotation(Validates.class);
			Class<?> target = validates.value();

			validateValidator(m, target);
			
			Class<?> c = m.getDeclaringClass();
			
			validatorClasses.put(target, c);
			validatorMethods.put(target, m);
			LOG.info("Added validator: {} for class: {}", m, target);
		}
	}

	private void validateValidator(Method m, Class<?> target) {
		Class<?>[] params = m.getParameterTypes();
		if( params.length == 0 || !target.isAssignableFrom(params[0])) {
			throw new IllegalStateException(String.format(
					"Method %s is marked with @Validates but first param is not type of %s", m, target));
		}
		if( params.length == 2 && !ValidationResult.class.isAssignableFrom(params[1])) {
			throw new IllegalStateException(String.format(
					"Method %s is marked with @Validates but second param is not %s",
					m, ValidationResult.class.getCanonicalName()));
		}
	}

	public Validator validatorFor(Class<?> clazz) {
		Class<?> vClass = validatorClasses.get(clazz);
		Method vMethod = validatorMethods.get(clazz);
		if (vClass != null) {
			Object vObject = injector.getInstance(vClass);
			return new AnnotatedMethodValidator(vObject, vMethod);
		}

		return new NoopValidator();
	}

	@SuppressWarnings("unchecked")
	public <T> void validate(T entity) {
		Class<T> c = (Class<T>) entity.getClass();
		validatorFor(c).validate(entity);
	}

	private static class NoopValidator implements Validator {
		public void validate(Object o) throws ValidationException {
		}
	}

}
