package com.spring.baitap10.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.baitap10.model.Category;
import com.spring.baitap10.service.ICategoryService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

	private ICategoryService categoryService;
	
	
	public CategoryController(ICategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@PostMapping(value = "/add")
	public ResponseEntity<Category> saveCategory(@RequestBody Category category){
		return new ResponseEntity<Category>(categoryService.save(category), HttpStatus.CREATED);
	}
	@GetMapping(value = "/all")
	public List<Category> getAllCategories(){
		return categoryService.getAll();
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findProductById(@PathVariable("id") long id){
		return new ResponseEntity<Category>(categoryService.getCategoryById(id), HttpStatus.OK);
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category ,@PathVariable("id") long id){
		return new ResponseEntity<Category>(categoryService.updateCategory(category, id), HttpStatus.CREATED); 
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") long id){
		categoryService.deleteCategory(id);
		return new ResponseEntity<String>("Delete category successfully", HttpStatus.OK); 
	}
	@GetMapping(value = "/find")
	public List<Category> findCategoryByName(@RequestParam(value = "name", required = false) String name){
		return (categoryService.findByNameContaining(name));
	}
	@GetMapping(value = "/page")
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
									 @RequestParam(value = "size", defaultValue = "4") Integer size) {
		PageRequest request = PageRequest.of(page - 1, size);
		return new ResponseEntity<>(categoryService.fildAll(request),HttpStatus.OK);
	}
}
