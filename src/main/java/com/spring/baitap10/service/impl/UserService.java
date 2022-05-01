package com.spring.baitap10.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.spring.baitap10.DTO.Request.SendEmail;
import com.spring.baitap10.model.Cart;
import com.spring.baitap10.model.User;
import com.spring.baitap10.repository.CartRepo;
import com.spring.baitap10.repository.UserRepo;
import com.spring.baitap10.service.IUserService;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService implements IUserService{

	@Autowired
	UserRepo userRepo;
	@Autowired
	CartRepo cartRepo;
	@Autowired
    JavaMailSender javaMailSender;
	@Override
	public Optional<User> findByUsername(String name) {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(name);
	}

	@Override
	public boolean existsByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepo.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.existsByEmail(email);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		User savedUser = userRepo.save(user);

        // initial Cart
        Cart savedCart = cartRepo.save(new Cart(savedUser));
        savedUser.setCart(savedCart);
		return userRepo.save(savedUser);
	}

	@Override
	public User update(long id, User user) {
		// TODO Auto-generated method stub
		Optional<User> find = userRepo.findById(id);
		if(find !=null) {
			find.get().setName(user.getName());
			find.get().setPhone(user.getPhone());
			find.get().setAddress(user.getAddress());
			userRepo.save(find.get());
		}
		return null;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

	@Override
	public void addVerificationCode(String email) throws UnsupportedEncodingException, MessagingException {
		// TODO Auto-generated method stub
		 String code = RandomString.make(64);
        User user = userRepo.findByEmail(email).get();
        user.setVerificationCode(code);
        userRepo.save(user);
        this.sendVerificationEmailForResetPassWord(user.getEmail(), code);
	}
	
	public void sendVerificationEmailForResetPassWord(String email, String randomCode) throws UnsupportedEncodingException, MessagingException {
        String subject = "Hãy xác thực email của bạn";
        String mailContent = "";
        String confirmUrl = "http://localhost:4200/verify-reset-password?code=" + randomCode;


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom("rigobertoprouty9866@gmail.com","CỬA HÀNG ĐIỆN THOẠI MdShop");
        helper.setTo(email);
        helper.setSubject(subject);
        User user = userRepo.findByEmail(email).get();
        mailContent = "<p sytle='color:red;'>Xin chào " + user.getName() + " ,<p>" + "<p> Nhấn vào link sau để xác thực email của bạn:</p>" +
                "<h3><a href='" + confirmUrl + "'>Click vào đây để xác nhận lại mật khẩu!</a></h3>" +
                "<p>CỬA HÀNG ĐIỆN THOẠI MdShop</p>";
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }

	@Override
	public Optional<User> findByVerificationCode(String code) {
		// TODO Auto-generated method stub
		return userRepo.findByVerificationCode(code);
	}

	@Override
	public void saveNewPassword(String password, String code) {
		// TODO Auto-generated method stub
		User user = userRepo.findByVerificationCode(code).get();
		user.setPassword(password);
		user.setVerificationCode(null);
		userRepo.save(user);
		
	}

	@Override
	public Page<User> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return userRepo.findAll(pageable);
	}

	@Override
	public void deleteUser(long id) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(id).get();
		System.out.println(user);
		if(user != null) {
//			user.setCart(null);
			
			user.setRoles(null);
			System.out.println(user.getCart().getUser().getId());
			userRepo.deleteById(id);
		}
	}

	@Override
	public Optional<User> findById(long id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id);
	}

	@Override
	public void sendEmail(SendEmail sendEmail) throws MailException, MessagingException {
		// TODO Auto-generated method stub
		MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom(sendEmail.getEmail());
        helper.setTo("rigobertoprouty9866@gmail.com");
        helper.setSubject(sendEmail.getSubject());
        String mailContent = "<p sytle='color:red;'>Tôi là " + sendEmail.getName() + " ,<p>" + "<p>" + sendEmail.getMessage() + "</p>";
        helper.setText(mailContent, true);
        javaMailSender.send(message);
	}
}
