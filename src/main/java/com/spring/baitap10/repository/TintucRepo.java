package com.spring.baitap10.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.baitap10.model.TinTuc;

@Repository
public interface TintucRepo extends JpaRepository<TinTuc, Long>{
	Page<TinTuc> findAll(Pageable pageable);
}
