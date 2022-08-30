package com.spring.baitap10.service;

import java.util.Optional;

import com.spring.baitap10.model.Role;
import com.spring.baitap10.model.RoleName;

public interface RoleService {
	Optional<Role> findByName(RoleName name);
}
