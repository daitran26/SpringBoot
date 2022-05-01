package com.spring.baitap10.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "productinorder")
@Data
@NoArgsConstructor
public class ProductInOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long p_id;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Cart cart;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private OrderMain orderMain;
	
    private long id;
   
    private String name;
    @Lob
    private String description;
    private String image;
  
    private Long category_id;
   
    private BigDecimal price;
  
    private String title;
    @Min(1)
    private Integer count;
    public ProductInOrder(Product product, Integer quantity) {
    	this.id = product.getId();
    	this.name = product.getName();
    	this.description = product.getDescription();
    	this.image = product.getImage();
    	this.category_id = product.getCategory().getId();
    	this.price = BigDecimal.valueOf(product.getPrice()-product.getPrice()*product.getDiscount()/100);
    	this.title = product.getTitle();
    	this.count = quantity;
    }
	@Override
	public String toString() {
		return "ProductInOrder [p_id=" + p_id + ", id=" + id + ", name=" + name + ", description=" + description
				+ ", image=" + image + ", category_id=" + category_id + ", price=" + price + ", title=" + title
				+ ", count=" + count + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ProductInOrder other = (ProductInOrder) obj;
		return 	p_id == other.p_id 
				&& id == other.id
				&& Objects.equals(name, other.name)
				&& Objects.equals(description, other.description)
				&& Objects.equals(image, other.image) 
				&& Objects.equals(title, other.title)
				&& Objects.equals(category_id, other.category_id) 
				&& Objects.equals(price, other.price) ;
	}
	@Override
	public int hashCode() {
		return Objects.hash(p_id, id, name, description, image, title, category_id, price);
	}
	
}
