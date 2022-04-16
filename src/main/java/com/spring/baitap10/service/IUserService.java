package com.spring.baitap10.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;

import com.spring.baitap10.DTO.Request.SendEmail;
import com.spring.baitap10.model.User;
public interface IUserService {
	Optional<User> findByUsername(String name);
	Optional<User> findByEmail(String email);
	Optional<User> findByVerificationCode(String code);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	User save(User user);
	User update(long id, User user);
	void addVerificationCode(String username) throws MessagingException, UnsupportedEncodingException;
	void saveNewPassword(String password,String code);
	Page<User> getAll(Pageable pageable);
	void deleteUser(long id);
	Optional<User> findById(long id);
	void sendEmail(SendEmail sendEmail) throws MailException,MessagingException;
}
