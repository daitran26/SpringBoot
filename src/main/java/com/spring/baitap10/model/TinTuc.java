package com.spring.baitap10.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tintuc")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class TinTuc implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5864429046656468149L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	@Lob
	private String description;
	@Lob
	private String content;
	@Lob
	private String image;
}
