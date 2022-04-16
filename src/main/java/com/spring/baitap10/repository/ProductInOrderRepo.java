package com.spring.baitap10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.baitap10.model.ProductInOrder;

@Repository
public interface ProductInOrderRepo extends JpaRepository<ProductInOrder, Long>{
	void deleteById(Long id);
}
