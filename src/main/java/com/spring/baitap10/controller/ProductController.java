package com.spring.baitap10.controller;

import com.spring.baitap10.DTO.Respone.ResponeMessage;
import com.spring.baitap10.common.Response;
import com.spring.baitap10.common.ResponseBody;
import com.spring.baitap10.model.Product;
import com.spring.baitap10.model.User;
import com.spring.baitap10.security.userprincal.UserDetailService;
import com.spring.baitap10.service.IProductService;
import com.spring.baitap10.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private UserDetailService userDetailService;
	
	@PostMapping(value = "/add")
	public ResponseEntity<?> saveProduct(@RequestBody Product product){
		User user = userDetailService.getCurrentUser();
		if(user != null && user.getUsername() != null && !user.getUsername().equals("Anonymous")) {
			if(product.getImage() == null || product.getImage().trim().isEmpty()) {
				return new ResponseEntity<>(new ResponeMessage("noavatar"),HttpStatus.OK);
			}
			productService.save(product);
			return new ResponseEntity<>(new ResponeMessage("yes"),HttpStatus.CREATED);
		}
		if(user != null && user.getUsername().equals("Anonymous")) {
			return new ResponseEntity<>(new ResponeMessage("no"),HttpStatus.OK);
		}
		return new ResponseEntity<>(new ResponeMessage("falied"),HttpStatus.OK);
	}
	@GetMapping(value = "/all")
	public List<Product> getAllProducts(){
		return productService.getAll();
	}
	@GetMapping(value = "/top4")
	public List<Product> getTop4Products(){
		return productService.findHotProduct();
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponseBody> findProductById(@PathVariable("id") long id){
		return ResponseEntity.ok(new ResponseBody(Response.SUCCESS,productService.getProductById(id)));
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product ,@PathVariable("id") long id){
		return new ResponseEntity<>(productService.updateProduct(product,id), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>("Delete product successfully", HttpStatus.OK);
	}
	@GetMapping(value = "/search")
	public ResponseEntity<?> searchProduct(@RequestParam(value = "page", defaultValue = "1") Integer page,
			   						   	   @RequestParam(value = "size", defaultValue = "4") Integer size,
			   						   	   @RequestParam(value = "name") String name) {
		PageRequest request = PageRequest.of(page - 1, size);
		return new ResponseEntity<>(productService.findByNameContaining(name, request),HttpStatus.OK);
	}
	@GetMapping(value = "/sort-price")
	public ResponseEntity<?> sortPrice(@RequestParam(value = "page", defaultValue = "1") Integer page,
			 						   @RequestParam(value = "size", defaultValue = "4") Integer size,
			 						   @RequestParam(value = "min") Double min,
			 						   @RequestParam(value = "max") Double max) {
		Sort sort = Sort.by(Direction.ASC,"price");
//		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
		PageRequest request = PageRequest.of(page - 1, size,sort);
		return new ResponseEntity<>(productService.findByPriceBetween(min, max, request),HttpStatus.OK);
	}
	@GetMapping(value = "/page-sort")
	public ResponseEntity<?> pageProductSort(@RequestParam(value = "page", defaultValue = "1") Integer page,
            							 	@RequestParam(value = "size", defaultValue = "4") Integer size,
            							 	@RequestParam(value = "name", defaultValue = "id") String name,
            							 	@RequestParam(value = "type",defaultValue = "ASC") String type){
		Sort sort = Sort.by(Direction.fromString(type),name);
		PageRequest request = PageRequest.of(page - 1, size,sort);
        return new ResponseEntity<>(productService.findAll(request),HttpStatus.OK);
	}
	@GetMapping(value = "/category/{id}")
	public ResponseEntity<?> findAllByCategoryID(@PathVariable("id") long categoryId,
									          	  @RequestParam(value = "page", defaultValue = "1") Integer page,
									          	  @RequestParam(value = "size", defaultValue = "4") Integer size,
									          	  @RequestParam(value = "name", defaultValue = "id") String name,
	            							 	  @RequestParam(value = "type",defaultValue = "ASC") String type) {
		Sort sort = Sort.by(Direction.fromString(type),name);
		PageRequest request = PageRequest.of(page - 1, size,sort);
		return new ResponseEntity<>(productService.findAllByCategoryId(categoryId, request),HttpStatus.OK);
	}
}
