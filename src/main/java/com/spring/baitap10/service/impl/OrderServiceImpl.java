package com.spring.baitap10.service.impl;

import com.spring.baitap10.model.OrderMain;
import com.spring.baitap10.model.Product;
import com.spring.baitap10.model.ProductInOrder;
import com.spring.baitap10.repository.OrderMainRepo;
import com.spring.baitap10.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderMainRepo orderMainRepo;
	@Autowired
    ProductServiceImpl productService;
	@Override
	public Page<OrderMain> findAll(Pageable pageable) {
		return orderMainRepo.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
	}

	@Override
	public Page<OrderMain> findByStatus(Integer status, Pageable pageable) {
		return orderMainRepo.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
	}

	@Override
	public Page<OrderMain> findByBuyerEmail(String email, Pageable pageable) {
		return orderMainRepo.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
	}

	@Override
	public Page<OrderMain> findByBuyerPhone(String phone, Pageable pageable) {
		return orderMainRepo.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
	}

	@Override
	public OrderMain findOne(Long id) {
		return orderMainRepo.getById(id);
	}

	@Override
	@Transactional
	public OrderMain finish(Long orderId) {
		OrderMain orderMain = findOne(orderId);
		if(!orderMain.getOrderStatus().equals(0)) {
			return null;
		}
		orderMain.setOrderStatus(1);
		orderMainRepo.save(orderMain);
		return orderMainRepo.getById(orderId);
	}
	@Override
	@Transactional
	public OrderMain paypal(Long orderId) {
		OrderMain orderMain = findOne(orderId);
		if(!orderMain.getOrderStatus().equals(0)) {
			return null;
		}
		orderMain.setOrderStatus(1);
		orderMain.setPaypal(1);
		orderMainRepo.save(orderMain);
		return orderMainRepo.getById(orderId);
	}
	@Override
	@Transactional
	public OrderMain cancel(Long orderId) {
		OrderMain orderMain = findOne(orderId);
		if(!orderMain.getOrderStatus().equals(0)) {
			return null;
		}
		orderMain.setOrderStatus(2);
		orderMainRepo.save(orderMain);
		Iterable<ProductInOrder> products = orderMain.getProducts();
        for(ProductInOrder productInOrder : products) {
            Product productInfo = null;
            if(productInfo != null) {
                try {
					productService.increaseStock(productInOrder.getId(), productInOrder.getCount());
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
		return orderMainRepo.getById(orderId);
	}

	@Override
	public Page<OrderMain> findByBuyerName(String username, Pageable pageable) {
		return orderMainRepo.findAllByBuyerNameOrderByOrderStatusAscCreateTimeDesc(username, pageable);
	}

	@Override
	public Optional<OrderMain> findById(Long id) {
		return orderMainRepo.findById(id);
	}


	@Override
	public List<OrderMain> findAll() {
		return orderMainRepo.findAll();
	}

}
