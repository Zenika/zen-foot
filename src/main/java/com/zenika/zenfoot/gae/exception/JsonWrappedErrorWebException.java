package com.zenika.zenfoot.gae.exception;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import restx.RestxRequest;
import restx.RestxResponse;
import restx.WebException;
import restx.http.HttpStatus;

public class JsonWrappedErrorWebException extends WebException {
	
	private static final long serialVersionUID = 5403840423992164772L;
	
	private final ErrorHolder errorHolder;
	
	public JsonWrappedErrorWebException(String errorCode, String errorMessage) {
		this(HttpStatus.BAD_REQUEST, errorCode, errorMessage);
	}

	public JsonWrappedErrorWebException(HttpStatus status, String errorCode, String errorMessage) {
		super(status);
		this.errorHolder = new ErrorHolder(errorCode, errorMessage);
	}

	@Override
	public void writeTo(RestxRequest restxRequest, RestxResponse restxResponse) throws IOException {
		restxResponse.setStatus(getStatus());
        restxResponse.setContentType("application/json");
        restxResponse.getWriter().print(errorHolder);
	}

	private class ErrorHolder {
		
		private final String errorCode;
		
		private final String errorMessage;

		public ErrorHolder(String errorCode, String errorMessage) {
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
		}
		
		public String getErrorCode() {
			return errorCode;
		}

		public String getErrorMessage() {
			return errorMessage;
		}
		
		@Override
		public String toString() {
			try {
				return new ObjectMapper().writeValueAsString(this);
			} catch (JsonProcessingException jpe) {
				return "";
			}
		}
		
	}
	
}
