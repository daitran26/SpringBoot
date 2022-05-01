package com.spring.baitap10.service.impl;

import java.util.List;

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
	public Product updateProduct(Product product, long id) {
		// TODO Auto-generated method stub
		Product p = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product", "ID", id));
		p.setName(product.getName());
		p.setImage(product.getImage());
		p.setPrice(product.getPrice());
		p.setSoluong(product.getSoluong());
		p.setDiscount(product.getDiscount());
		p.setTitle(product.getTitle());
		p.setDescription(product.getDescription());
		p.setCategory(product.getCategory());
		productRepository.save(p);
//		for (int i = 0; i < productRepository.findAll().size(); i++) {
//			if(productRepository.findAll().get(i).getId() == p.getId()) {
//				productRepository.findAll().set(i, p);
//				break;
//			}
//		}
		return p;
		
	}

	@Override
	public void deleteProduct(long id) {
		// TODO Auto-generated method stub
		Product p = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product", "ID", id));
		productRepository.delete(p);
	}

	@Override
	public List<Product> findAllByCategotyId(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findAllByCategotyId(id);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findAll(pageable);
	}

	@Override
	public List<Product> findHotProduct() {
		// TODO Auto-generated method stub
		return productRepository.findTop4ByOrderByIdDesc();
	}

	@Override
	public List<Product> findAllProduct(int index) {
		// TODO Auto-generated method stub
		return productRepository.findAllProduct(index);
	}

	@Override
	public List<Product> findAllByCategotyId(Long id, int index) {
		// TODO Auto-generated method stub
		return productRepository.findAllByCategotyId(id, index);
	}

	@Override
	public Page<Product> findByNameContaining(String name,Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findByNameContaining(name,pageable);
	}

	@Override
	public List<Product> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return productRepository.findAll(sort);
	}

	@Override
	public Page<Product> findAllByCategory_id(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findAllByCategory_id(id,pageable);
	}

	@Override
	public Page<Product> findByPriceBetween(double min, double max, Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findByPriceBetween(min, max, pageable);
	}

	@Override
	@Transactional
	public void increaseStock(long productId, int amount) throws Exception{
		// TODO Auto-generated method stub
		Product productInfo = getProductById(productId);
        if (productInfo == null) throw new Exception("product not found"+productId);

        int update = productInfo.getSoluong() + amount;

        productInfo.setSoluong(update);
        productRepository.save(productInfo);
		
	}

	@Override
	@Transactional
	public void decreaseStock(long productId, int amount) throws Exception{
		// TODO Auto-generated method stub
		Product productInfo = getProductById(productId);
        if (productInfo == null) throw new Exception("product not found"+productId);

        int update = productInfo.getSoluong() - amount;
        if(update < 0) throw new Exception();

        productInfo.setSoluong(update);
        productRepository.save(productInfo);
	}
}
