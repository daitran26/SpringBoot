package com.spring.baitap10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.baitap10.model.OrderMain;

public interface OrderService {
	Page<OrderMain> findAll(Pageable pageable);

    Page<OrderMain> findByStatus(Integer status, Pageable pageable);

    Page<OrderMain> findByBuyerEmail(String email, Pageable pageable);
    Page<OrderMain> findByBuyerName(String username, Pageable pageable);

    Page<OrderMain> findByBuyerPhone(String phone, Pageable pageable);

    OrderMain findOne(Long orderId);

    Optional<OrderMain> findById(Long id);
    OrderMain finish(Long orderId);
    OrderMain paypal(Long orderId);

    OrderMain cancel(Long orderId);
    
    long totalMonth(int month);
    List<OrderMain> findAll();
}
