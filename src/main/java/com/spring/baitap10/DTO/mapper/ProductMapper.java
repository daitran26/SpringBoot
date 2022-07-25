package com.spring.baitap10.DTO.mapper;

import com.spring.baitap10.DTO.ProductDto;
import com.spring.baitap10.model.Product;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);
    List<Product> toEntity(List<ProductDto> productDtos);
    List<ProductDto> toDto(List<Product> products);
}
