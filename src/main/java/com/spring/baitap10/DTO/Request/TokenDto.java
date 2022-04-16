package com.spring.baitap10.DTO.Request;

public class TokenDto {
	private String value;

	public TokenDto() {
		super();
	}

	public TokenDto(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
