package com.spring.baitap10.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.baitap10.model.OrderMain;

@Repository
public interface OrderMainRepo extends JpaRepository<OrderMain,Long>{
	    Page<OrderMain> findAllByOrderStatusOrderByCreateTimeDesc(Integer orderStatus, Pageable pageable);
	    Page<OrderMain> findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(String buyerEmail, Pageable pageable);
	    Page<OrderMain> findAllByOrderByOrderStatusAscCreateTimeDesc(Pageable pageable);
	    Page<OrderMain> findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(String buyerPhone, Pageable pageable);
	    Page<OrderMain> findAllByBuyerNameOrderByOrderStatusAscCreateTimeDesc(String username, Pageable pageable);
	    
	    @Query(value = "select SUM(order_amount) from ordermain u where u.order_status = 1 and MONTH(u.update_time)=?1", nativeQuery = true)
	    long totalMonth(int month);
}
