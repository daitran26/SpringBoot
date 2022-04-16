package com.spring.baitap10.DTO.Request;

public class ChangeAvatar {
	private String avatar;

	public ChangeAvatar(String avatar) {
		super();
		this.avatar = avatar;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public ChangeAvatar() {
		super();
	}
	
}
