package com.spring.baitap10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import com.spring.baitap10.DTO.Respone.ResponeMessage;
import com.spring.baitap10.model.Product;
import com.spring.baitap10.model.User;
import com.spring.baitap10.security.userprincal.UserDetailService;
import com.spring.baitap10.service.IProductService;


@CrossOrigin(origins = "http://localhost:4200") // ghép 2 nền tảng
@RestController // sự kết hợp giữa Controller và responeBody
@RequestMapping(value = "/api/product")
public class ProductController {
	
	@Autowired
	private IProductService productService;
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
	public ResponseEntity<Product> findProductById(@PathVariable("id") long id){
		return ResponseEntity.ok(productService.getProductById(id));
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
	@GetMapping(value = "/find")
	public List<Product> findAllByCategotyId(@RequestParam(value = "cate", required = true) Long id) {
		return productService.findAllByCategotyId(id);
	}
	
	//paging
	@GetMapping(value = "/page")
	public ResponseEntity<?> pageProduct(@RequestParam(value = "page", defaultValue = "1") Integer page,
            							 @RequestParam(value = "size", defaultValue = "4") Integer size){
		PageRequest request = PageRequest.of(page - 1, size);
        return new ResponseEntity<>(productService.findAll(request),HttpStatus.OK);
	}
	@GetMapping(value = "/page1")
	public ResponseEntity<?> pageProduct1(@PageableDefault(sort = "soluong",direction = Sort.Direction.DESC) Pageable pageable){
		return new ResponseEntity<>(productService.findAll(pageable),HttpStatus.OK);
	}
	@GetMapping(value = "/paging")
	public ResponseEntity<?> page(@RequestParam(value = "index") int index){
		List<Product> paging = productService.findAllProduct(index);
		if(paging.isEmpty()) {
			return new ResponseEntity<>(new ResponeMessage("no"),HttpStatus.OK);
		}
		return new ResponseEntity<>(paging,HttpStatus.OK);
	}
	@GetMapping(value = "/find-paging")
	public List<Product> findAllByCategotyId(@RequestParam(value = "cate", required = true) Long id,@RequestParam(value = "index") int index) {
		return productService.findAllByCategotyId(id,index);
	}
	@GetMapping(value = "/search")
	public ResponseEntity<?> searchProduct(@RequestParam(value = "page", defaultValue = "1") Integer page,
			   						   	   @RequestParam(value = "size", defaultValue = "4") Integer size,
			   						   	   @RequestParam(value = "name") String name) {
		PageRequest request = PageRequest.of(page - 1, size);
		return new ResponseEntity<>(productService.findByNameContaining(name, request),HttpStatus.OK);
	}
	@GetMapping(value = "/sort")
	public List<Product> sort(@RequestParam(value = "name", required = true) String name,@RequestParam(value = "type") String type) {
		Sort sort = Sort.by(Direction.fromString(type),name);
		return productService.findAll(sort);
	}
	@GetMapping(value = "/sort-price")
	public ResponseEntity<?> sortPrice(@RequestParam(value = "page", defaultValue = "1") Integer page,
			 						   @RequestParam(value = "size", defaultValue = "4") Integer size,
			 						   @RequestParam(value = "min") Double min,
			 						   @RequestParam(value = "max") Double max) {
		Sort sort = Sort.by(Direction.ASC,"price");
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
