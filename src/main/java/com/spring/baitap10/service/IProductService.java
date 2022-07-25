package com.spring.baitap10.service;

import com.spring.baitap10.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
	Product save(Product product);
	List<Product> getAll();
	Product getProductById(Long id);
	Product updateProduct(Product product, long id);
	void deleteProduct(long id);
	Page<Product> findAll(Pageable pageable);
	List<Product> findHotProduct();
	Page<Product> findByNameContaining(String name,Pageable pageable);

	Page<Product> findAllByCategoryId(Long id,Pageable pageable);
	Page<Product> findByPriceBetween(double min, double max, Pageable pageable);
	
	void increaseStock(long productId, int amount) throws Exception;

    //decrease stock
    void decreaseStock(long productId, int amount) throws Exception;
}
