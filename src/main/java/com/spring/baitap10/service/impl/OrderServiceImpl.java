package com.spring.baitap10.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.baitap10.model.OrderMain;
import com.spring.baitap10.model.Product;
import com.spring.baitap10.model.ProductInOrder;
import com.spring.baitap10.repository.OrderMainRepo;
import com.spring.baitap10.repository.ProductRepository;
import com.spring.baitap10.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderMainRepo orderMainRepo;
	@Autowired
	ProductService productService;
	@Override
	public Page<OrderMain> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return orderMainRepo.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
	}

	@Override
	public Page<OrderMain> findByStatus(Integer status, Pageable pageable) {
		// TODO Auto-generated method stub
		return orderMainRepo.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
	}

	@Override
	public Page<OrderMain> findByBuyerEmail(String email, Pageable pageable) {
		// TODO Auto-generated method stub
		return orderMainRepo.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
	}

	@Override
	public Page<OrderMain> findByBuyerPhone(String phone, Pageable pageable) {
		// TODO Auto-generated method stub
		return orderMainRepo.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
	}

	@Override
	public OrderMain findOne(Long id) {
		// TODO Auto-generated method stub
		return orderMainRepo.getById(id);
	}

	@Override
	@Transactional
	public OrderMain finish(Long orderId) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		OrderMain orderMain = findOne(orderId);
		if(!orderMain.getOrderStatus().equals(0)) {
			return null;
		}
		orderMain.setOrderStatus(2);
		orderMainRepo.save(orderMain);
		Iterable<ProductInOrder> products = orderMain.getProducts();
        for(ProductInOrder productInOrder : products) {
            Product productInfo = productService.getProductById(productInOrder.getId());
            if(productInfo != null) {
                try {
					productService.increaseStock(productInOrder.getId(), productInOrder.getCount());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
//        return orderRepository.findByOrderId(orderId);
		return orderMainRepo.getById(orderId);
	}

	@Override
	public Page<OrderMain> findByBuyerName(String username, Pageable pageable) {
		// TODO Auto-generated method stub
		return orderMainRepo.findAllByBuyerNameOrderByOrderStatusAscCreateTimeDesc(username, pageable);
	}

	@Override
	public Optional<OrderMain> findById(Long id) {
		// TODO Auto-generated method stub
		return orderMainRepo.findById(id);
	}

	@Override
	public long totalMonth(int month) {
		// TODO Auto-generated method stub
		System.out.println("asssssssssssss" + orderMainRepo.totalMonth(month));
		return orderMainRepo.totalMonth(month)!=0 ? orderMainRepo.totalMonth(month) : 0;
	}

	@Override
	public List<OrderMain> findAll() {
		// TODO Auto-generated method stub
		return orderMainRepo.findAll();
	}

}
