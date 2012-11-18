package com.fiestacabin.model.test.secondparam;

import com.fiestacabin.model.test.Foo;
import com.fiestacabin.model.validator.Validates;
import com.fiestacabin.model.validator.ValidationResult;

public class FooValidator {

	@Validates(Foo.class)
	public void validate(Foo t, ValidationResult result) {
		result.required("field", "field value", t.getField());
	}

}
