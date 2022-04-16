package com.spring.baitap10.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.baitap10.model.TinTuc;
import com.spring.baitap10.repository.TintucRepo;
import com.spring.baitap10.service.TintucService;

@Service
public class TintucServiceImpl implements TintucService{
	
	@Autowired
	TintucRepo tintucRepo;
	@Override
	public TinTuc save(TinTuc tinTuc) {
		// TODO Auto-generated method stub
		return tintucRepo.save(tinTuc);
	}

	@Override
	public List<TinTuc> findAll() {
		// TODO Auto-generated method stub
		return tintucRepo.findAll();
	}

	@Override
	public Page<TinTuc> pageTintuc(Pageable pageable) {
		// TODO Auto-generated method stub
		return tintucRepo.findAll(pageable);
	}

	@Override
	public TinTuc getDetail(Long id) {
		// TODO Auto-generated method stub
		return tintucRepo.getById(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		tintucRepo.deleteById(id);
	}

	@Override
	public TinTuc update(long id, TinTuc tinTuc) {
		// TODO Auto-generated method stub
		TinTuc x = tintucRepo.getById(id);
		if(x != null) {
			x.setTitle(tinTuc.getTitle());
			x.setImage(tinTuc.getImage());
			x.setDescription(tinTuc.getDescription());
			x.setContent(tinTuc.getContent());
			tintucRepo.save(x);
			return x;
		}
		return null;
	}

}
