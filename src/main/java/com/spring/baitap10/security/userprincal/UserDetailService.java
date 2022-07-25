package com.spring.baitap10.security.userprincal;

import com.spring.baitap10.model.User;
import com.spring.baitap10.repository.UserRepo;
import com.spring.baitap10.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND : " + username));
        return UserPrinciple.build(user);
    }

    @Transactional
    public User getCurrentUser() {
        Optional<User> user;
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        if (userRepo.existsByUsername(userName)) {
            user = userService.findByUsername(userName);
        } else {
            user = Optional.of(new User());
            user.get().setUsername("Anonymous");
        }
        if (user.isPresent()) return user.get();
        return null;
    }
}
