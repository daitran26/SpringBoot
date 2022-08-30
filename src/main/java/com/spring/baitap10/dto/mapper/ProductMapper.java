package com.spring.baitap10.dto.mapper;

import com.spring.baitap10.dto.ProductDto;
import com.spring.baitap10.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);
    List<Product> toEntity(List<ProductDto> productDtos);
    List<ProductDto> toDto(List<Product> products);
}
