package com.spring.baitap10.service.impl;

import com.spring.baitap10.exception.ResourceNotFoundException;
import com.spring.baitap10.model.Category;
import com.spring.baitap10.repository.CategotyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements com.spring.baitap10.service.CategoryService {

	@Autowired
	private CategotyRepository categotyRepository;

	@Override
	public List<Category> getAll() {
		return categotyRepository.findAll();
	}
	@Override
	public Category getCategoryById(Long id) {
		return categotyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("category", "ID", id));
	}

	@Override
	public Category updateCategory(Category category, long id) {
		Category c = categotyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("category", "ID", id));
		if(c!=null) {
			c.setName(category.getName());
			categotyRepository.save(c);
		}
		return c;
	}

	@Override
	public Category save(Category category) {
		return categotyRepository.save(category);
	}

	@Override
	public void deleteCategory(long id) {
		Category c = categotyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("category", "ID", id));
		if(c!=null) {
			categotyRepository.delete(c);
		}
		
	}

	@Override
	public List<Category> findByNameContaining(String name) {
		return categotyRepository.findByNameContaining(name);
	}

	@Override
	public Page<Category> fildAll(Pageable pageable) {
		return categotyRepository.findAll(pageable);
	}
}
