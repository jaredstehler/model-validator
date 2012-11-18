package com.fiestacabin.model.validator;

public abstract class BaseValidator implements Validator {

	public void validate(Object o) throws ValidationException {
		ValidationResult invalidResult = new ValidationResult("Please correct errors in the form and resubmit.");

		doValidate(o, invalidResult);
		
		if( invalidResult.getGeneralErrors().size() > 0 || invalidResult.getFields().size() > 0 ){
			throw new ValidationException(invalidResult);
		}
	}

	public abstract void doValidate(Object o, ValidationResult result) throws ValidationException;
	
}
