package com.fiestacabin.model.validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.fiestacabin.model.test.Foo;
import com.fiestacabin.model.test.nosecondparam.FooValidator;
import com.google.inject.Injector;


public class ValidatorsTest {
	
	private Injector injector;
	private Validators validators;
	
	@Before
	public void init() {
		this.injector = mock(Injector.class);
		
		ValidatorConfiguration config = new ValidatorConfiguration();
		config.setBasePackage("com.fiestacabin.model.test.nosecondparam");
		this.validators = new Validators(config, injector);
	}
	
	
	@Test
	public void itReturnsNoopValidatorForAllUnknownCases() {
		Validator v = validators.validatorFor(Unknown.class);
		assertThat(v, notNullValue());
		v.validate(new Unknown());
	}
	
	@Test
	public void invokesSingleParamValidator() {
		FooValidator fv = mock(FooValidator.class);
		when(injector.getInstance(FooValidator.class)).thenReturn(fv);

		Foo f = new Foo("bar");
		validators.validate(f);

		verify(fv).validate(f);
	}
	
	@Test
	public void invokesTwoParamValidator() {
		com.fiestacabin.model.test.secondparam.FooValidator fv = mock(com.fiestacabin.model.test.secondparam.FooValidator.class);
		when(injector.getInstance(com.fiestacabin.model.test.secondparam.FooValidator.class)).thenReturn(fv);
		
		Foo f = new Foo("bar");
		new Validators(new ValidatorConfiguration("com.fiestacabin.model.test.secondparam"), injector).validate(f);
		verify(fv).validate(eq(f), any(ValidationResult.class));
	}

	@Test(expected=IllegalStateException.class)
	public void itThrowsIllegalStateWhenWrongFirstParam() {
		new Validators(new ValidatorConfiguration("com.fiestacabin.model.test.wrongfirstparam"), injector);
	}

	@Test(expected=IllegalStateException.class)
	public void itThrowsIllegalStateWhenWrongNoParams() {
		new Validators(new ValidatorConfiguration("com.fiestacabin.model.test.wrongnoparams"), injector);
	}
	
	@Test(expected=IllegalStateException.class)
	public void itThrowsIllegalStateWhenWrongSecondParam() {
		new Validators(new ValidatorConfiguration("com.fiestacabin.model.test.wrongsecondparam"), injector);
	}
	
	public static class Unknown {}
}
