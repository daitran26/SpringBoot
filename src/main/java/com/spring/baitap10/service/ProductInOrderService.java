package com.spring.baitap10.service;

import com.spring.baitap10.model.ProductInOrder;
import com.spring.baitap10.model.User;


public interface ProductInOrderService {
	void update(Long itemId, Integer quantity, User user);
    ProductInOrder findOne(Long itemId, User user);
    ProductInOrder save(ProductInOrder productInOrder);
    void deleteById(Long id);
    void deleteProductInorder(long id);
}
