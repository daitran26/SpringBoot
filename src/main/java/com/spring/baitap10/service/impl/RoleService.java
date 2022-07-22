package com.spring.baitap10.service.impl;

import com.spring.baitap10.model.Role;
import com.spring.baitap10.model.RoleName;
import com.spring.baitap10.repository.RoleRepo;
import com.spring.baitap10.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService{

	@Autowired
	RoleRepo roleRepo;

	@Override
	public Optional<Role> findByName(RoleName name) {
		return roleRepo.findByName(name);
	}
}
