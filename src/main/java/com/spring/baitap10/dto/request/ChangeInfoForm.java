package com.spring.baitap10.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeInfoForm {
	private String name;
	private String avatar;
	private String phone;
	private String address;
	private Set<String> roles;
}
