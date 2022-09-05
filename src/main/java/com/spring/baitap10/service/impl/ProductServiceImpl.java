package com.spring.baitap10.service.impl;

import java.util.List;

import com.spring.baitap10.dto.ProductDto;
import com.spring.baitap10.dto.mapper.ProductMapper;
import com.spring.baitap10.common.Response;
import com.spring.baitap10.dto.request.SearchProductRequestDto;
import com.spring.baitap10.exception.CommonException;
import com.spring.baitap10.service.ProductService;
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

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public ProductDto saveOrUpdate(ProductDto productDto) {
		User user = userDetailService.getCurrentUser();
		productDto.setUser(user);
		return this.productMapper.toDto(productRepository.save(this.productMapper.toEntity(productDto)));
	}

	@Override
	public ProductDto findOne(Long id) {
		return productRepository.findById(id).map(this.productMapper::toDto).orElseThrow(()-> new CommonException(Response.OBJECT_NOT_FOUND));
	}

	@Override
	public void delete(long id) {
		productRepository.delete(this.productMapper.toEntity(findOne(id)));
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
	@Transactional(rollbackFor = Exception.class)
	public void increaseStock(long productId, int amount) throws Exception{
		Product productInfo = null;
        int update = productInfo.getSoluong() + amount;
        productInfo.setSoluong(update);
        productRepository.save(productInfo);
		
	}

	@Override
	@Transactional
	public void decreaseStock(long productId, int amount) throws Exception{
		Product productInfo = null;
        if (productInfo == null) throw new Exception("product not found"+productId);

        int update = productInfo.getSoluong() - amount;
        if(update < 0) throw new Exception();

        productInfo.setSoluong(update);
        productRepository.save(productInfo);
	}

	@Override
	public Page<ProductDto> pageProduct(SearchProductRequestDto searchProductRequestDto) {
		try {
			return productRepository.search(searchProductRequestDto.getName(),searchProductRequestDto.getCategoryId(), searchProductRequestDto.getPageable())
					.map(this.productMapper::toDto);
		}
		catch (Exception e){
			throw e;
		}
	}
}
