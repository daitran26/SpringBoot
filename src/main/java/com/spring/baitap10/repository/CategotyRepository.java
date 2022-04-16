package com.spring.baitap10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.baitap10.model.Category;

public interface CategotyRepository extends JpaRepository<Category, Long>{
	List<Category> findByNameContaining(String name);
}
