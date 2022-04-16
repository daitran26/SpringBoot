package com.spring.baitap10.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.baitap10.exception.ResourceNotFoundException;
import com.spring.baitap10.model.Category;
import com.spring.baitap10.repository.CategotyRepository;
import com.spring.baitap10.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService{

	private CategotyRepository categotyRepository;

	public CategoryService(CategotyRepository categotyRepository) {
		super();
		this.categotyRepository = categotyRepository;
	}

	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return categotyRepository.findAll();
	}
	@Override
	public Category getCategoryById(Long id) {
		return categotyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("category", "ID", id));
	}

	@Override
	public Category updateCategory(Category category, long id) {
		// TODO Auto-generated method stub
		Category c = categotyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("category", "ID", id));
		if(c!=null) {
			c.setName(category.getName());
		}
		categotyRepository.save(c);
		return c;
	}

	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
		return categotyRepository.save(category);
	}

	@Override
	public void deleteCategory(long id) {
		// TODO Auto-generated method stub
		Category c = categotyRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("category", "ID", id));
		if(c!=null) {
			categotyRepository.delete(c);
		}
		
	}

	@Override
	public List<Category> findByNameContaining(String name) {
		// TODO Auto-generated method stub
		return categotyRepository.findByNameContaining(name);
	}

	@Override
	public Page<Category> fildAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return categotyRepository.findAll(pageable);
	}
}
