package com.spring.baitap10.service.impl;

import com.spring.baitap10.dto.request.SendEmail;
import com.spring.baitap10.model.Cart;
import com.spring.baitap10.model.User;
import com.spring.baitap10.repository.CartRepo;
import com.spring.baitap10.repository.UserRepo;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserServiceImpl implements com.spring.baitap10.service.UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepo.findByUsername(name);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        User savedUser = userRepo.save(user);

        Cart savedCart = cartRepo.save(new Cart(savedUser));
        savedUser.setCart(savedCart);
        return userRepo.save(savedUser);
    }

    @Override
    public User update(long id, User user) {
        Optional<User> find = userRepo.findById(id);
        if (find.isPresent()) {
            find.get().setName(user.getName());
            find.get().setPhone(user.getPhone());
            find.get().setAddress(user.getAddress());
            userRepo.save(find.get());
            return find.get();
        }
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void addVerificationCode(String email) throws UnsupportedEncodingException, MessagingException {
        String code = RandomString.make(64);
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            user.get().setVerificationCode(code);
            userRepo.save(user.get());
            this.sendVerificationEmailForResetPassWord(user.get().getEmail(), code);
        }
    }

    public void sendVerificationEmailForResetPassWord(String email, String randomCode) throws UnsupportedEncodingException, MessagingException {
        String subject = "Hãy xác thực email của bạn";
        String mailContent = "";
        String confirmUrl = "http://localhost:4200/verify-reset-password?code=" + randomCode;


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom("rigobertoprouty9866@gmail.com", "CỬA HÀNG ĐIỆN THOẠI MdShop");
        helper.setTo(email);
        helper.setSubject(subject);
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            mailContent = "<p sytle='color:red;'>Xin chào " + user.get().getName() + " ,<p>" + "<p> Nhấn vào link sau để xác thực email của bạn:</p>" +
                    "<h3><a href='" + confirmUrl + "'>Click vào đây để xác nhận lại mật khẩu!</a></h3>" +
                    "<p>CỬA HÀNG ĐIỆN THOẠI MdShop</p>";
        }
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    @Override
    public Optional<User> findByVerificationCode(String code) {
        return userRepo.findByVerificationCode(code);
    }

    @Override
    public void saveNewPassword(String password, String code) {
        Optional<User> user = userRepo.findByVerificationCode(code);
        if (user.isPresent()) {
            user.get().setPassword(password);
            user.get().setVerificationCode(null);
            userRepo.save(user.get());
        }
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepo.findById(id).get();
        if (user != null) {
            user.setRoles(null);
            userRepo.deleteById(id);
        }
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepo.findById(id);
    }

    @Override
    public void sendEmail(SendEmail sendEmail) throws MailException, MessagingException {
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
