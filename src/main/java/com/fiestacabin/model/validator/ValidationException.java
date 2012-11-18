package com.fiestacabin.model.validator;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ValidationException extends WebApplicationException {
	private static final long serialVersionUID = 1L;

	private ValidationResult invalidResult;

	public ValidationException(ValidationResult invalidResult){
		this(invalidResult, null);
	}
	
	public ValidationException(ValidationResult invalidResult, Throwable t) {
		super(t);
		this.invalidResult = invalidResult;
	}

	public ValidationResult getInvalidResult() {
		return invalidResult;
	}

	@Override
	public Response getResponse() {
		return Response.status(Response.Status.BAD_REQUEST)
					   .type(MediaType.APPLICATION_JSON)
				       .entity(invalidResult)
				       .build();
 	}

}
