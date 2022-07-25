package com.spring.baitap10.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tintuc")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class TinTuc{

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
