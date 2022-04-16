package com.spring.baitap10.DTO.Request;

public class ResetPassword {
	private String password;
	private String code;
	public ResetPassword() {
		super();
	}
	public ResetPassword(String password, String code) {
		super();
		this.password = password;
		this.code = code;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
