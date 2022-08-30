package com.spring.baitap10.service.impl;

import com.spring.baitap10.model.Cart;
import com.spring.baitap10.model.OrderMain;
import com.spring.baitap10.model.ProductInOrder;
import com.spring.baitap10.model.User;
import com.spring.baitap10.repository.CartRepo;
import com.spring.baitap10.repository.OrderMainRepo;
import com.spring.baitap10.repository.ProductInOrderRepo;
import com.spring.baitap10.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;


@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	CartRepo cartRepo;
	@Autowired
	ProductInOrderRepo productInOrderRepo;
	@Autowired
	OrderMainRepo orderMainRepo;
	@Autowired
	OrderServiceImpl orderServiceImpl;
	@Autowired
    ProductServiceImpl productService;
	@Override
	public Cart getCart(User user) {
		return user.getCart();
	}

	@Override
	@Transactional
	public void mergeLocalCart(Collection<ProductInOrder> productInOrders, User user) {
		Cart cart = user.getCart();
		productInOrders.forEach(product ->{
			Set<ProductInOrder> set = cart.getProducts();
			Optional<ProductInOrder> op = set.stream().filter(p -> p.getId()==product.getId()).findFirst();
			ProductInOrder productInOrder;
			if(op.isPresent()) {
				productInOrder = op.get();
				productInOrder.setCount(productInOrder.getCount() + product.getCount());
			}
			else {
				productInOrder = product;
				productInOrder.setCart(cart);
				cart.getProducts().add(productInOrder);
			}
			productInOrderRepo.save(productInOrder);
		});
	}

	@Override
	public void delete(Long itemId, User user) {
		Optional<ProductInOrder> op = user.getCart().getProducts().stream().filter(p -> p.getId() == itemId).findFirst();
		if(op.isPresent()) {
			op.get().setCart(null);
			productInOrderRepo.delete(op.get());
		}
	}

	@Override
	public Set<ProductInOrder> checkout(User user) {
		OrderMain order = new OrderMain(user);
        orderMainRepo.save(order);

        user.getCart().getProducts().forEach(productInOrder -> {
            productInOrder.setCart(null);
            productInOrder.setOrderMain(order);
			try {
				productService.decreaseStock(productInOrder.getId(), productInOrder.getCount());
			} catch (Exception e) {
				e.printStackTrace();
			}
            productInOrderRepo.save(productInOrder);
        });
        return user.getCart().getProducts();
	}
	@Override
	public void checkoutpaypal(User user) {
		OrderMain order = new OrderMain(user);
        orderMainRepo.save(order);

        // clear cart's foreign key & set order's foreign key& decrease stock
        user.getCart().getProducts().forEach(productInOrder -> {
            productInOrder.setCart(null);
            productInOrder.setOrderMain(order);
            try {
				productService.decreaseStock(productInOrder.getId(), productInOrder.getCount());
			} catch (Exception e) {
				e.printStackTrace();
			}
            productInOrderRepo.save(productInOrder);
        });
        orderServiceImpl.paypal(order.getId());
	}

}
