package com.fiestacabin.model.validator;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.common.collect.Lists;

@JsonSerialize(include=NON_NULL)
public class ValidationResult {

	private String message;
	private List<String> generalErrors = Lists.newLinkedList();
	private List<InvalidField> fields = Lists.newLinkedList();

	public ValidationResult(){}

	public ValidationResult(String message) {
		this.message = message;
	}
	
	public ValidationResult addField(InvalidField f) {
		fields.add(f);
		return this;
	}
	
	public ValidationResult reject(String reason) {
		generalErrors.add(reason);
		return this;
	}
	
	public ValidationResult rejectField(String field, String reason) {
		return addField(new InvalidField(field, reason));
	}
	
	public ValidationResult required(String field, String name, Object val) {
		if(val == null || ( val instanceof String && ((String) val).trim().length() == 0 )){
			rejectField(field, String.format("%s is required", name));
		}
		
		return this;
	}
	
	public List<String> getGeneralErrors() {
		return generalErrors;
	}
	
	public List<InvalidField> getFields() {
		return fields;
	}
	
	public String getMessage() {
		return message;
	}
	
}
