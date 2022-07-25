package com.spring.baitap10.model;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product")
@Data
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String image;
	private double price;
	@ColumnDefault("100")
	private int soluong;
	@ColumnDefault("0")
	private int discount;
	private String title;
	@Lob
	private String description;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	public Product() {
		super();
	}
	public Product(long id, String name, String image, double price,int soluong,int discount, String title, String description,
			Category category, User user) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.price = price;
		this.soluong = soluong;
		this.discount = discount;
		this.title = title;
		this.description = description;
		this.category = category;
		this.user = user;
	}
	
}
