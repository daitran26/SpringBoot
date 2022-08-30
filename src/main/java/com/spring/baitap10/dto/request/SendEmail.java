package com.spring.baitap10.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmail {
	private String name;
	private String email;
	private String subject;
	private String message;
}
