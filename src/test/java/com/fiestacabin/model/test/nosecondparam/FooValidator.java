package com.fiestacabin.model.test.nosecondparam;

import com.fiestacabin.model.test.Foo;
import com.fiestacabin.model.validator.Validates;
import com.fiestacabin.model.validator.ValidationException;
import com.fiestacabin.model.validator.ValidationResult;

public class FooValidator {

	@Validates(Foo.class)
	public void validate(Foo t) throws ValidationException {
		throw new ValidationException(new ValidationResult("I find this foo to be invalid"));
	}
	
}
