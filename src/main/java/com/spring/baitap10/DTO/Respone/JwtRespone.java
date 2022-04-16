package com.spring.baitap10.DTO.Respone;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.spring.baitap10.model.User;

import lombok.Data;

@Data
public class JwtRespone {
	String token;
	String type = "Bearer";
	private String name;
	private String avatar;
	private User user;
	private Collection<? extends GrantedAuthority> roles;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}
	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}
	
	public JwtRespone() {
		super();
	}
	public JwtRespone(String token, String name, String avatar,User user,
			Collection<? extends GrantedAuthority> roles) {
		super();
		this.token = token;
		this.name = name;
		this.avatar = avatar;
		this.user = user;
		this.roles = roles;
	}
}
