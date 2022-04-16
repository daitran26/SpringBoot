package com.spring.baitap10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.baitap10.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String name);
	Optional<User> findByEmail(String email);
	Optional<User> findByVerificationCode(String code);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
