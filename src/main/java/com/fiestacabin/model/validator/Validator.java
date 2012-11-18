package com.fiestacabin.model.validator;

public interface Validator {

	void validate(Object o) throws ValidationException;
	
}
