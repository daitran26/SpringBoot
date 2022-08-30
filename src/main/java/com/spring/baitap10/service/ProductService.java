package com.spring.baitap10.service;

import java.util.List;

import com.spring.baitap10.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.spring.baitap10.model.Product;

public interface ProductService {
	Product save(Product product);
	List<Product> getAll();
	Product getProductById(Long id);
	ProductDto updateProduct(ProductDto productDto);
	void deleteProduct(long id);
	List<Product> findAllByCategotyId(Long id);
	List<Product> findAllByCategotyId(Long id, int index);
	Page<Product> findAll(Pageable pageable);
	List<Product> findHotProduct();
	List<Product> findAllProduct(int index);
	
	Page<Product> findByNameContaining(String name,Pageable pageable);
	
	List<Product> findAll(Sort sort);
	
	
	//page
	Page<Product> findAllByCategory_id(Long id,Pageable pageable);
	Page<Product> findByPriceBetween(double min, double max, Pageable pageable);
	
	void increaseStock(long productId, int amount) throws Exception;

    //decrease stock
    void decreaseStock(long productId, int amount) throws Exception;
}
