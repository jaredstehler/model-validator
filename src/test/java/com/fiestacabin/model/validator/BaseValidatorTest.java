package com.fiestacabin.model.validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import javax.ws.rs.core.Response;

import org.junit.Test;


public class BaseValidatorTest {
	
	@Test
	public void noErrors() {
		new NoErrorValidator().validate("foo");
	}
	
	@Test(expected=ValidationException.class)
	public void fieldError() {
		new FieldErrorValidator().validate("foo");
	}
	
	@Test(expected=ValidationException.class)
	public void generalError() {
		new GeneralErrorValidator().validate("foo");
	}

	@Test
	public void setsInvalidResultInException() {
		try {
			new FieldErrorValidator().validate("foo");
		}
		catch(ValidationException e) {
			assertThat(e.getInvalidResult().getFields().size(), is(1));
		}
	}
	
	@Test
	public void exceptionReturnsResponseWithInvalidResultEntity() {
		try {
			new FieldErrorValidator().validate("foo");
		}
		catch(ValidationException e) {
			Response r = e.getResponse();
			assertThat(r.getStatus(), is(400));
			assertThat(r.getEntity(), is(instanceOf(ValidationResult.class)));
		}
	}
	
	private static class NoErrorValidator extends BaseValidator {
		@Override
		public void doValidate(Object o, ValidationResult result)
				throws ValidationException {}
	}
	
	private static class FieldErrorValidator extends BaseValidator {
		@Override
		public void doValidate(Object o, ValidationResult result)
				throws ValidationException {
			result.rejectField("f", "reason");
		}
	}
	
	private static class GeneralErrorValidator extends BaseValidator {
		@Override
		public void doValidate(Object o, ValidationResult result)
				throws ValidationException {
			result.reject("foo");
		}
	}
}
