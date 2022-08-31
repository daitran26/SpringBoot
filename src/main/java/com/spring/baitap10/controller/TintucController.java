package com.spring.baitap10.controller;

import com.spring.baitap10.common.Response;
import com.spring.baitap10.common.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.baitap10.model.TinTuc;
import com.spring.baitap10.service.TintucService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tintuc")
public class TintucController {

	@Autowired
	private TintucService tintucService;
	@GetMapping
	public ResponseEntity<List<TinTuc>> listTintuc(){
		return new ResponseEntity<>(tintucService.findAll(),HttpStatus.OK);
	}
	@GetMapping("/page")
	public ResponseEntity<Page<TinTuc>> listTintuc(@RequestParam(value = "page", defaultValue = "1") Integer page,
										   @RequestParam(value = "size", defaultValue = "4") Integer size){
		PageRequest request = PageRequest.of(page - 1, size);
		return new ResponseEntity<>(tintucService.pageTintuc(request),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<TinTuc> getDetail(@PathVariable(value = "id") Long id){
		return new ResponseEntity<>(tintucService.getDetail(id),HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<TinTuc> add(@RequestBody @Valid TinTuc tinTuc){
		return new ResponseEntity<>(tintucService.save(tinTuc),HttpStatus.CREATED);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseBody> delete(@PathVariable(value = "id") Long id){
		tintucService.delete(id);
		return new ResponseEntity<>(new ResponseBody(Response.SUCCESS),HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<TinTuc> update(@PathVariable(value = "id") Long id,@RequestBody TinTuc tinTuc){
		return new ResponseEntity<>(tintucService.update(id, tinTuc),HttpStatus.CREATED);
	}
}
