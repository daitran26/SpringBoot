package com.spring.baitap10.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.baitap10.model.TinTuc;

public interface TintucService {
	TinTuc save(TinTuc tinTuc);
	List<TinTuc> findAll();
	Page<TinTuc> pageTintuc(Pageable pageable);
	TinTuc getDetail(Long id);
	void delete(Long id);
	TinTuc update(long id, TinTuc tinTuc);
}
