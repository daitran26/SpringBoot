package com.spring.baitap10.controller;

import com.spring.baitap10.common.PageResponse;
import com.spring.baitap10.common.Response;
import com.spring.baitap10.common.ResponseBody;
import com.spring.baitap10.dto.ProductDto;
import com.spring.baitap10.dto.mapper.ProductMapper;
import com.spring.baitap10.dto.request.SearchProductRequestDto;
import com.spring.baitap10.security.userprincal.UserDetailService;
import com.spring.baitap10.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
@Tag(name = "Quản lý sản phẩm")
public class ProductController {

    private final ProductService productService;

    private final UserDetailService userDetailService;

    @Autowired
    private ProductMapper productMapper;

    @Operation(summary = "API thêm mới sản phẩm (Quản lý Sản phẩm)")
    @PostMapping(value = "/add")
    public ResponseEntity<ResponseBody> saveProduct(@RequestBody @Valid ProductDto productDto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, productService.saveOrUpdate(productDto)));
    }

    @Operation(summary = "API get sản phẩm (Quản lý Sản phẩm)")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBody> findProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, productService.findOne(id)));
    }

    @Operation(summary = "API cập nhật sản phẩm (Quản lý Sản phẩm)")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseBody> updateProduct(@PathVariable(value = "id") final Long id,
                                                      @RequestBody ProductDto productDto) {
        productDto.setId(productService.findOne(id).getId());
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS,productService.saveOrUpdate(productDto)));
    }

    @Operation(summary = "API xóa sản phẩm (Quản lý Sản phẩm)")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseBody> deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return new ResponseEntity<>(new ResponseBody(Response.SUCCESS,"Xóa sản phẩm thành công."), HttpStatus.OK);
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

    @Operation(summary = "API tìm kiếm sản phẩm (Quản lý Sản phẩm)")
    @GetMapping("")
    public ResponseEntity<ResponseBody> getProduct(
            @ParameterObject @Valid SearchProductRequestDto searchProductRequestDto) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS,new PageResponse(productService.pageProduct(searchProductRequestDto))));
    }
}
