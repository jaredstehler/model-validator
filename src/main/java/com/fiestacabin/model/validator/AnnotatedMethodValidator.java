package com.fiestacabin.model.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.common.base.Throwables;

public class AnnotatedMethodValidator extends BaseValidator {

	private Object validator;
	private Method validationMethod;
	
	public AnnotatedMethodValidator(Object validator, Method validationMethod) {
		this.validator = validator;
		this.validationMethod = validationMethod;
	}

	@Override
	public void doValidate(Object o, ValidationResult result) throws ValidationException {
		try {
			if(validationMethod.getParameterTypes().length == 1){
				validationMethod.invoke(validator, o);
			}
			else {
				validationMethod.invoke(validator, o, result);
			}
		} catch (IllegalAccessException e) {
			throw Throwables.propagate(e);
		} catch (InvocationTargetException e) {
			throw Throwables.propagate(e);
		}
	}
	
}
