package com.fiestacabin.model.validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;


public class ValidationResultTest {

	@Test
	public void constructorSetsMessage() {
		ValidationResult r = new ValidationResult("msg");
		assertThat(r.getMessage(), is("msg"));
	}
	
	@Test
	public void generalRejection() {
		ValidationResult r = new ValidationResult();
		r.reject("foo");
		assertThat(r.getGeneralErrors().size(), is(1));
		assertThat(r.getGeneralErrors().get(0), is("foo"));
	}
	
	@Test
	public void fieldSpecificRejection() {
		ValidationResult r = new ValidationResult();
		r.rejectField("foo", "bar");
		assertThat(r.getFields().size(), is(1));
		assertThat(r.getFields().get(0).getField(), is("foo"));
		assertThat(r.getFields().get(0).getMessage(), is("bar"));
	}
	
	@Test
	public void requiredStringOk() {
		ValidationResult r = new ValidationResult();
		r.required("foo", "bar", "ok");
		assertThat(r.getFields().size(), is(0));
	}
	
	@Test
	public void requiredStringBlank() {
		ValidationResult r = new ValidationResult();
		r.required("foo", "bar", " ");
		assertThat(r.getFields().size(), is(1));
		assertThat(r.getFields().get(0).getField(), is("foo"));
		assertThat(r.getFields().get(0).getMessage(), is("bar is required"));
	}
	
	@Test
	public void requiredNull() {
		ValidationResult r = new ValidationResult();
		r.required("foo", "bar", null);
		assertThat(r.getFields().size(), is(1));
		assertThat(r.getFields().get(0).getField(), is("foo"));
		assertThat(r.getFields().get(0).getMessage(), is("bar is required"));
	}
	
	@Test
	public void requiredObjectOk() {
		ValidationResult r = new ValidationResult();
		r.required("o", "obj", new Object());
		assertThat(r.getFields().size(), is(0));
	}
}
