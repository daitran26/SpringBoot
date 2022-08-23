package com.spring.baitap10.controller;

import com.spring.baitap10.DTO.ProductDto;
import com.spring.baitap10.DTO.mapper.ProductMapper;
import com.spring.baitap10.common.PageResponse;
import com.spring.baitap10.common.Response;
import com.spring.baitap10.common.ResponseBody;
import com.spring.baitap10.model.Product;
import com.spring.baitap10.model.User;
import com.spring.baitap10.security.userprincal.UserDetailService;
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

    @Autowired
    private ProductMapper productMapper;

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseBody> saveProduct(@RequestBody ProductDto product) {
        User user = userDetailService.getCurrentUser();
        if (user != null && user.getUsername() != null && !user.getUsername().equals("Anonymous")) {
            if (product.getImage() == null || product.getImage().trim().isEmpty()) {
                return new ResponseEntity<>(new ResponseBody(Response.MISSING_PARAM), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, productService.save(productMapper.toEntity(product))));
        }
        return new ResponseEntity<>(new ResponseBody(Response.ERROR_AUTH_SYSTEM), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<ResponseBody> getAllProducts() {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS,productService.getAll()));
    }

    @GetMapping(value = "/top4")
    public List<Product> getTop4Products() {
        return productService.findHotProduct();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBody> findProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, productMapper.toDto(productService.getProductById(id))));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseBody> updateProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS,productService.updateProduct(productDto)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseBody> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(new ResponseBody(Response.SUCCESS,"Xóa sản phẩm thành công."), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<ResponseBody> searchProduct(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "4") Integer size,
                                           @RequestParam(value = "name") String name) {
        PageRequest request = PageRequest.of(page - 1, size);
        PageResponse<ProductDto> pageResponse = new PageResponse<>(
                productService.findByNameContaining(name,request).map(product -> productMapper.toDto(product)));
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS,pageResponse));
    }

    @GetMapping(value = "/sort-price")
    public ResponseEntity<?> sortPrice(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "4") Integer size,
                                       @RequestParam(value = "min") Double min,
                                       @RequestParam(value = "max") Double max) {
        Sort sort = Sort.by(Direction.ASC, "price");
        PageRequest request = PageRequest.of(page - 1, size, sort);
        return new ResponseEntity<>(productService.findByPriceBetween(min, max, request), HttpStatus.OK);
    }

    @GetMapping(value = "/page-sort")
    public ResponseEntity<ResponseBody> pageProductSort(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", defaultValue = "4") Integer size,
                                             @RequestParam(value = "name", defaultValue = "id") String name,
                                             @RequestParam(value = "type", defaultValue = "ASC") String type) {
        Sort sort = Sort.by(Direction.fromString(type), name);
        PageRequest request = PageRequest.of(page - 1, size, sort);
        PageResponse<ProductDto> pageResponse = new PageResponse<>(
                productService.findAll(request).map(product -> productMapper.toDto(product)));
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS,pageResponse));
    }

    @GetMapping(value = "/category/{id}")
    public ResponseEntity<ResponseBody> findAllByCategoryID(@PathVariable("id") long categoryId,
                                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "4") Integer size,
                                                 @RequestParam(value = "name", defaultValue = "id") String name,
                                                 @RequestParam(value = "type", defaultValue = "ASC") String type) {
        Sort sort = Sort.by(Direction.fromString(type), name);
        PageRequest request = PageRequest.of(page - 1, size, sort);
        PageResponse<ProductDto> pageResponse = new PageResponse<>(
                productService.findAllByCategory_id(categoryId,request).map(product -> productMapper.toDto(product)));
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS,pageResponse));
    }
}
