package com.spring.baitap10.service.impl;

import com.spring.baitap10.common.Response;
import com.spring.baitap10.exception.CommonException;
import com.spring.baitap10.exception.ResourceNotFoundException;
import com.spring.baitap10.model.Product;
import com.spring.baitap10.model.User;
import com.spring.baitap10.repository.ProductRepository;
import com.spring.baitap10.security.userprincal.UserDetailService;
import com.spring.baitap10.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserDetailService userDetailService;
	
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
		Optional<Product> product = productRepository.findById(id);
		if (!product.isPresent()){
			throw new CommonException(Response.OBJECT_NOT_FOUND);
		}
		return product.get();
	}

	@Override
	public Product updateProduct(Product product, long id) {
		Product p = productRepository.findById(id).orElseThrow(()-> new CommonException(Response.OBJECT_NOT_FOUND));
		p.setName(product.getName());
		p.setImage(product.getImage());
		p.setPrice(product.getPrice());
		p.setSoluong(product.getSoluong());
		p.setDiscount(product.getDiscount());
		p.setTitle(product.getTitle());
		p.setDescription(product.getDescription());
		p.setCategory(product.getCategory());
		productRepository.save(p);
		return p;
		
	}

	@Override
	public void deleteProduct(long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()){
			productRepository.delete(product.get());
		}
		else {
			throw new CommonException(Response.OBJECT_NOT_FOUND);
		}

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
	public Page<Product> findAllByCategoryId(Long id, Pageable pageable) {
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
        if (productInfo == null) throw new ResourceNotFoundException("Product","ID",productId);

        int update = productInfo.getSoluong() + amount;

        productInfo.setSoluong(update);
        productRepository.save(productInfo);
		
	}

	@Override
	@Transactional
	public void decreaseStock(long productId, int amount) throws Exception{
		Product productInfo = getProductById(productId);
        if (productInfo == null) throw new ResourceNotFoundException("Product","ID",productId);

        int update = productInfo.getSoluong() - amount;
        if(update < 0) throw new CommonException(Response.SYSTEM_ERROR);

        productInfo.setSoluong(update);
        productRepository.save(productInfo);
	}
}
