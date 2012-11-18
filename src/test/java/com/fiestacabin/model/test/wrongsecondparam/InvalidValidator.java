package com.fiestacabin.model.test.wrongsecondparam;

import com.fiestacabin.model.test.Foo;
import com.fiestacabin.model.validator.Validates;

public class InvalidValidator {

	@Validates(Foo.class)
	public void validate(Foo f, String b) {
		
	}
	
}
