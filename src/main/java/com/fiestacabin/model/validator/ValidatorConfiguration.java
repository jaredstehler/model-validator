package com.fiestacabin.model.validator;

public class ValidatorConfiguration {

	private String basePackage;

	public ValidatorConfiguration(){}
	
	public ValidatorConfiguration(String basePackage) {
		this.basePackage = basePackage;
	}
	
	public String getBasePackage() {
		return basePackage;
	}
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	
}
