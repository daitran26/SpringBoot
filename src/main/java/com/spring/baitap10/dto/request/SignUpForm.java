package com.spring.baitap10.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {
	private String name;
	private String username;
	private String password;
	private String email;
	private String avatar;
	private Set<String> roles;
}
