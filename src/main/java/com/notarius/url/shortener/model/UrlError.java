package com.notarius.url.shortener.model;

public class UrlError {
	private String field;
	private String value;
	private String message;

	public UrlError(String field, String value, String message) {
		super();
		this.field = field;
		this.value = value;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
