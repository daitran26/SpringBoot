package com.spring.baitap10.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.spring.baitap10.common.Response;
import com.spring.baitap10.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.baitap10.dto.request.ChangeAvatar;
import com.spring.baitap10.dto.request.ChangeInfoForm;
import com.spring.baitap10.dto.request.ChangePassword;
import com.spring.baitap10.dto.request.ResetPassword;
import com.spring.baitap10.dto.request.SendEmail;
import com.spring.baitap10.dto.request.SignInForm;
import com.spring.baitap10.dto.request.SignUpForm;
import com.spring.baitap10.dto.respone.JwtRespone;
import com.spring.baitap10.dto.respone.ResponeMessage;
import com.spring.baitap10.model.Role;
import com.spring.baitap10.model.RoleName;
import com.spring.baitap10.model.User;
import com.spring.baitap10.repository.UserRepo;
import com.spring.baitap10.security.jwt.JwtProvider;
import com.spring.baitap10.security.userprincal.UserDetailService;
import com.spring.baitap10.security.userprincal.UserPrinciple;
import com.spring.baitap10.service.impl.RoleServiceImpl;
import com.spring.baitap10.service.impl.UserServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    UserRepo userRepo;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponeMessage("nouser"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponeMessage("noemail"), HttpStatus.OK);
        }
        if (signUpForm.getAvatar() == null || signUpForm.getAvatar().trim().isEmpty()) {
            signUpForm.setAvatar("https://thumbs.dreamstime.com/b/default-avatar-profile-icon-vector-social-media-user-portrait-176256935.jpg");
        }
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getEmail(), signUpForm.getAvatar());
        Set<String> strRole = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRole.forEach(role -> {
            switch (role) {
                case "admin":
                    Role admin = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(admin);
                    break;
                case "pm":
                    Role pm = roleService.findByName(RoleName.PM).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(pm);
                    break;
                default:
                    Role user1 = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(user1);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponeMessage("Create success"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignInForm signInForm) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.createToken(authentication);
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            User user = userService.findByUsername(userPrinciple.getUsername()).orElseThrow(() -> new UsernameNotFoundException("username not found " + userPrinciple.getUsername()));
            return ResponseEntity.ok(new JwtRespone(token, userPrinciple.getName(), userPrinciple.getAvatar(), user, userPrinciple.getAuthorities()));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new ResponeMessage("authenticated fail"), HttpStatus.OK);
        }
    }

    @PutMapping("/change-avatar")
    public ResponseEntity<?> changeAvatar(HttpServletRequest request, @Valid @RequestBody ChangeAvatar changeAvatar) {
        User user;
        try {
            if (changeAvatar.getAvatar() == null) {
                return new ResponseEntity<>(new ResponeMessage("no"), HttpStatus.OK);
            } else {
                user = userDetailService.getCurrentUser();
                user.setAvatar(changeAvatar.getAvatar());
                userRepo.save(user);
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ResponeMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @Valid @RequestBody ChangePassword changePassword) {
        User user;
        try {
            if (changePassword.getNewPassword() == null) {
                throw new CommonException(Response.MISSING_PARAM);
            } else {
                user = userDetailService.getCurrentUser();
                if (passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
                    userRepo.save(user);
                    return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ResponeMessage("password invalid"), HttpStatus.OK);
                }
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ResponeMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getuser")
    public ResponseEntity<?> findByUsername(@RequestBody User user) {
        return new ResponseEntity<>(userService.findByUsername(user.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        userService.update(id, user);
        return new ResponseEntity<>(new ResponeMessage("update success"), HttpStatus.OK);
    }

    @PostMapping("/google")
//    public ResponseEntity<?> google(@RequestBody TokenDto tokenDto) throws IOException,Exception {
//        final NetHttpTransport transport = new NetHttpTransport();
//        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
//        GoogleIdTokenVerifier.Builder verifier =
//                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
//                .setAudience(Collections.singletonList("898234275690-0qk6pl13nkbvepuihbvmmvf41dmjdbjf.apps.googleusercontent.com"));
//        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), tokenDto.getValue());
//        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
//        User usuario = new User();
//        if(userService.existsByEmail(payload.getEmail())) {
//        	usuario = userService.findByUsername(payload.getEmail()).get();
//        	System.out.println(usuario);
//        }
//        else {
//        	Set<String> h = new HashSet<String>();
//        	h.add("USER");
//        	SignUpForm signUpForm = new SignUpForm(payload.getOrDefault("name", "Ano").toString(),
//        			payload.getEmail(),payload.getEmail(),payload.getEmail(),
//        			payload.getOrDefault("picture", "https://thumbs.dreamstime.com/b/default-avatar-profile-icon-vector-social-media-user-portrait-176256935.jpg").toString(),h);
//            usuario = signup(signUpForm);
//        }
//        SignInForm signInForm = new SignInForm(usuario.getUsername(),usuario.getUsername());
//        return signin(signInForm);
//    }
    public User signup(@Valid @RequestBody SignUpForm signUpForm) {
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getEmail(), signUpForm.getAvatar());
        Set<String> strRole = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRole.forEach(role -> {
            switch (role) {
                case "admin":
                    Role admin = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(admin);
                    break;
                case "pm":
                    Role pm = roleService.findByName(RoleName.PM).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(pm);
                    break;
                default:
                    Role user1 = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(user1);
            }
        });
        user.setRoles(roles);
        return userService.save(user);
    }

    @GetMapping("/getusertoken")
    public ResponseEntity<?> getUserByTokenOK() {
        return new ResponseEntity<>(userDetailService.getCurrentUser(), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> reset(@RequestBody SignInForm signInForm) throws MessagingException, UnsupportedEncodingException {
        if (userService.existsByEmail(signInForm.getUsername())) {
            userService.addVerificationCode(signInForm.getUsername());
            Optional<User> user = userService.findByUsername(signInForm.getUsername());
            if (user.isPresent())
                return ResponseEntity.ok(user.get());
            return ResponseEntity
                    .badRequest()
                    .body(new ResponeMessage("USER_NOT_FOUND"));
        }
        return ResponseEntity
                .badRequest()
                .body(new ResponeMessage("invalid user"));
    }

    @PostMapping("/do-reset-password")
    public ResponseEntity<?> doResetPassword(@RequestBody ResetPassword resetPassword) {
        userService.saveNewPassword(passwordEncoder.encode(resetPassword.getPassword()), resetPassword.getCode());
        return ResponseEntity.ok(new ResponeMessage("success"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> pageUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", defaultValue = "4") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(userService.getAll(request));
    }

    @GetMapping("/usercode/{code}")
    public ResponseEntity<?> findByVerificationCode(@PathVariable("code") String id) {
        Optional<User> user = userService.findByVerificationCode(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.ok("USER_NOT_FOUND");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findByUsername(@PathVariable("id") String id) {
        Optional<User> user = userService.findByUsername(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.ok("USER_NOT_FOUND");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponeMessage("Delete success"));
    }

    @PutMapping("/change-role/{id}")
    public ResponseEntity<?> changeRole(@PathVariable("id") long id, @RequestBody ChangeInfoForm user) {
        Optional<User> user3 = userService.findById(id);
        if (user3.isPresent()) {
            User user2 = user3.get();
            user2.setName(user.getName());
            user2.setAvatar(user.getAvatar());
            user2.setPhone(user.getPhone());
            user2.setAddress(user.getAddress());
            Set<String> strRole = user.getRoles();
            Set<Role> roles = new HashSet<>();
            strRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role admin = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                        roles.add(admin);
                        break;
                    case "pm":
                        Role pm = roleService.findByName(RoleName.PM).orElseThrow(() -> new RuntimeException("Role not found"));
                        roles.add(pm);
                        break;
                    default:
                        Role user1 = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                        roles.add(user1);
                }
            });
            user2.setRoles(roles);
            userRepo.save(user2);
            return new ResponseEntity<>(new ResponeMessage("update success"), HttpStatus.OK);
        }
        return ResponseEntity.ok("USER_NOT_FOUND");
    }

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestBody SendEmail sendEmail) throws MailException, MessagingException {
        userService.sendEmail(sendEmail);
        return ResponseEntity.ok(new ResponeMessage("Send success"));
    }
}
