package com.spring.baitap10.service.impl;

import com.spring.baitap10.model.TinTuc;
import com.spring.baitap10.repository.TintucRepo;
import com.spring.baitap10.service.TintucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TintucServiceImpl implements TintucService {

    @Autowired
    TintucRepo tintucRepo;

    @Override
    public TinTuc save(TinTuc tinTuc) {
        return tintucRepo.save(tinTuc);
    }

    @Override
    public List<TinTuc> findAll() {
        return tintucRepo.findAll();
    }

    @Override
    public Page<TinTuc> pageTintuc(Pageable pageable) {
        return tintucRepo.findAll(pageable);
    }

    @Override
    public TinTuc getDetail(Long id) {
        return tintucRepo.getById(id);
    }

    @Override
    public void delete(Long id) {
        tintucRepo.deleteById(id);
    }

    @Override
    public TinTuc update(long id, TinTuc tinTuc) {
        TinTuc x = tintucRepo.getById(id);
        x.setTitle(tinTuc.getTitle());
        x.setImage(tinTuc.getImage());
        x.setDescription(tinTuc.getDescription());
        x.setContent(tinTuc.getContent());
        tintucRepo.save(x);
        return x;
    }
}
