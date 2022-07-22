package com.spring.baitap10.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.baitap10.model.ProductInOrder;
import com.spring.baitap10.model.User;
import com.spring.baitap10.repository.ProductInOrderRepo;
import com.spring.baitap10.service.ProductInOrderService;


@Service
public class ProductInOrderImpl implements ProductInOrderService{
	@Autowired
	ProductInOrderRepo productInOrderRepo;
	@Override
	public void update(Long itemId, Integer quantity, User user) {
		Optional<ProductInOrder> op = user.getCart().getProducts().stream().filter(item -> itemId.equals(item.getId())).findFirst();
		op.ifPresent(product -> {
			product.setCount(quantity);
			productInOrderRepo.save(product);
		});		
	}

	@Override
	public ProductInOrder findOne(Long itemId, User user) {
		Optional<ProductInOrder> op = user.getCart().getProducts().stream().filter(item -> itemId.equals(item.getId())).findFirst();
		if (op.isPresent()) return op.get();
		return null;
	}

	@Override
	public ProductInOrder save(ProductInOrder productInOrder) {
		return productInOrderRepo.save(productInOrder);
	}

	@Override
	public void deleteById(Long id) {
		productInOrderRepo.deleteById(id);
	}

	@Override
	public void deleteProductInorder(long id) {

	}

}
