package com.spring.baitap10.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.spring.baitap10.model.TinTuc;
import com.spring.baitap10.service.TintucService;

@CrossOrigin
@RestController
@RequestMapping("/tintuc")
public class TintucController {

	@Autowired
	private TintucService tintucService;
	@GetMapping
	public ResponseEntity<?> listTintuc(){
		return new ResponseEntity<>(tintucService.findAll(),HttpStatus.OK);
	}
	@GetMapping("/page")
	public ResponseEntity<?> listTintuc(@RequestParam(value = "page", defaultValue = "1") Integer page,
			 							@RequestParam(value = "size", defaultValue = "4") Integer size){
		PageRequest request = PageRequest.of(page - 1, size);
		return new ResponseEntity<>(tintucService.pageTintuc(request),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getDetail(@PathVariable(value = "id") Long id){
		return new ResponseEntity<>(tintucService.getDetail(id),HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody TinTuc tinTuc){
		return new ResponseEntity<>(tintucService.save(tinTuc),HttpStatus.CREATED);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
		tintucService.delete(id);
		return new ResponseEntity<>("delete success",HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") Long id,@RequestBody TinTuc tinTuc){
		return new ResponseEntity<>(tintucService.update(id, tinTuc),HttpStatus.CREATED);
	}
}
