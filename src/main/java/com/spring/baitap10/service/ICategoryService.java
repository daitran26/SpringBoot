package com.spring.baitap10.service;

import com.spring.baitap10.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
	List<Category> getAll();
	Category getCategoryById(Long id);
	Category updateCategory(Category category, long id);
	Category save(Category category);
	void deleteCategory(long id);
	List<Category> findByNameContaining(String name);
	Page<Category> fildAll(Pageable pageable);
}
