package com.spring.baitap10.service.impl;

import java.util.List;

import com.spring.baitap10.DTO.ProductDto;
import com.spring.baitap10.DTO.mapper.ProductMapper;
import com.spring.baitap10.common.Response;
import com.spring.baitap10.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.baitap10.exception.ResourceNotFoundException;
import com.spring.baitap10.model.Product;
import com.spring.baitap10.model.User;
import com.spring.baitap10.repository.ProductRepository;
import com.spring.baitap10.security.userprincal.UserDetailService;
import com.spring.baitap10.service.IProductService;

@Service
public class ProductService implements IProductService{

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public Product save(Product product) {
		User user = userDetailService.getCurrentUser();
		product.setUser(user);
		return productRepository.save(product);
	}
	
	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product", "ID", id));
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto) {
		Product product = productRepository.findById(productDto.getId())
				.map(p -> this.productMapper.toEntity(productDto)).map(this.productRepository::save)
				.orElseThrow(()-> new CommonException(Response.OBJECT_NOT_FOUND));

		return productMapper.toDto(product);
	}

	@Override
	public void deleteProduct(long id) {
		Product p = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product", "ID", id));
		productRepository.delete(p);
	}

	@Override
	public List<Product> findAllByCategotyId(Long id) {
		return productRepository.findAllByCategotyId(id);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public List<Product> findHotProduct() {
		return productRepository.findTop4ByOrderByIdDesc();
	}

	@Override
	public List<Product> findAllProduct(int index) {
		return productRepository.findAllProduct(index);
	}

	@Override
	public List<Product> findAllByCategotyId(Long id, int index) {
		return productRepository.findAllByCategotyId(id, index);
	}

	@Override
	public Page<Product> findByNameContaining(String name,Pageable pageable) {
		return productRepository.findByNameContaining(name,pageable);
	}

	@Override
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public Page<Product> findAllByCategory_id(Long id, Pageable pageable) {
		return productRepository.findAllByCategory_id(id,pageable);
	}

	@Override
	public Page<Product> findByPriceBetween(double min, double max, Pageable pageable) {
		return productRepository.findByPriceBetween(min, max, pageable);
	}

	@Override
	@Transactional
	public void increaseStock(long productId, int amount) throws Exception{
		Product productInfo = getProductById(productId);
        if (productInfo == null) throw new Exception("product not found"+productId);

        int update = productInfo.getSoluong() + amount;

        productInfo.setSoluong(update);
        productRepository.save(productInfo);
		
	}

	@Override
	@Transactional
	public void decreaseStock(long productId, int amount) throws Exception{
		Product productInfo = getProductById(productId);
        if (productInfo == null) throw new Exception("product not found"+productId);

        int update = productInfo.getSoluong() - amount;
        if(update < 0) throw new Exception();

        productInfo.setSoluong(update);
        productRepository.save(productInfo);
	}
}
