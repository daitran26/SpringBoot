package com.spring.baitap10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.baitap10.model.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long>{

}
