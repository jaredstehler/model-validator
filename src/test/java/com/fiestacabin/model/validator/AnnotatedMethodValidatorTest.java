package com.fiestacabin.model.validator;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;


public class AnnotatedMethodValidatorTest {

	@Test(expected=RuntimeException.class)
	public void propagatesIllegalAccessException() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method m = mock(Method.class);
		doThrow(IllegalAccessException.class).when(m.invoke(any(), anyObject()));
		
		new AnnotatedMethodValidator(new Object(), m).doValidate(new Object(), null);
	}
	
	@Test(expected=RuntimeException.class)
	public void propagatesInvocationTargetException() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		Method m = MyValidator.class.getDeclaredMethod("validate", Object.class);
		new AnnotatedMethodValidator(new MyValidator(), m).doValidate(new Object(), null);
	}

	static class MyValidator {
		public void validate(Object o) {
			throw new NullPointerException();
		}
	}
}
