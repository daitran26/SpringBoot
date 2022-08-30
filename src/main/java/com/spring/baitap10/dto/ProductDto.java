package com.spring.baitap10.dto;

import com.spring.baitap10.model.Category;
import com.spring.baitap10.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductDto {
    private long id;
    private String name;
    private String image;
    private double price;
    private int soluong;
    private int discount;
    private String title;
    private String description;
    private Category category;
    private User user;
}
