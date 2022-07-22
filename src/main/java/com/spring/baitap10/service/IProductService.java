package com.spring.baitap10.service;

import com.spring.baitap10.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IProductService {
	Product save(Product product);
	List<Product> getAll();
	Product getProductById(Long id);
	Product updateProduct(Product product, long id);
	void deleteProduct(long id);
	List<Product> findAllByCategotyId(Long id);
	List<Product> findAllByCategotyId(Long id, int index);
	Page<Product> findAll(Pageable pageable);
	List<Product> findHotProduct();
	List<Product> findAllProduct(int index);
	
	Page<Product> findByNameContaining(String name,Pageable pageable);
	
	List<Product> findAll(Sort sort);
	
	
	//page
	Page<Product> findAllByCategoryId(Long id,Pageable pageable);
	Page<Product> findByPriceBetween(double min, double max, Pageable pageable);
	
	void increaseStock(long productId, int amount) throws Exception;

    //decrease stock
    void decreaseStock(long productId, int amount) throws Exception;
}
