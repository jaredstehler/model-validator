package com.fiestacabin.model.validator;

import com.google.common.base.Objects;



public class InvalidField {

	private String field;
	private String message;
	
	public InvalidField(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("field", field)
				.add("message", message)
				.toString();
	}
	
}
