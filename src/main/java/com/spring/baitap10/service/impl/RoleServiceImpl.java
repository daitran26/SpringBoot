package com.spring.baitap10.service.impl;

import com.spring.baitap10.model.Role;
import com.spring.baitap10.model.RoleName;
import com.spring.baitap10.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements com.spring.baitap10.service.RoleService {

	@Autowired
	RoleRepo roleRepo;

	@Override
	public Optional<Role> findByName(RoleName name) {
		return roleRepo.findByName(name);
	}
}
