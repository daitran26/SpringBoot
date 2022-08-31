package com.spring.baitap10.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
	@NotNull(message = "Title không được để trống")
	private String title;
	@Lob
	@NotNull(message = "Description không được để trống")
	private String description;
	@Lob
	@NotNull(message = "Content không được để trống")
	private String content;
	@Lob
	@NotNull(message = "Image không được để trống")
	private String image;
}
