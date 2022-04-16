package com.spring.baitap10.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.baitap10.model.Role;
import com.spring.baitap10.model.RoleName;
import com.spring.baitap10.repository.RoleRepo;
import com.spring.baitap10.service.IRoleService;

@Service
public class RoleService implements IRoleService{

	@Autowired
	RoleRepo roleRepo;

	@Override
	public Optional<Role> findByName(RoleName name) {
		// TODO Auto-generated method stub
		return roleRepo.findByName(name);
	}
}
