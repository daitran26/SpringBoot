package com.spring.baitap10.service;

import java.util.Collection;

import com.spring.baitap10.model.Cart;
import com.spring.baitap10.model.ProductInOrder;
import com.spring.baitap10.model.User;


public interface CartService {
	Cart getCart(User user);

    void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user);

    void delete(Long itemId, User user);

    void checkout(User user);
}
