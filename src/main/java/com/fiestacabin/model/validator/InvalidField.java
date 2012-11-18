package com.fiestacabin.model.validator;



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

}
