package com.spring.baitap10.controller;

import com.spring.baitap10.model.OrderMain;
import com.spring.baitap10.service.OrderService;
import com.spring.baitap10.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/order")
    public Page<OrderMain> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderMain> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            orderPage = orderService.findByBuyerName(authentication.getName(), request);
        } else {
            orderPage = orderService.findAll(request);
        }
        return orderPage;
    }

    @PatchMapping("/order/cancel/{id}")
    public ResponseEntity<OrderMain> cancel(@PathVariable("id") Long orderId, Authentication authentication) {
        Optional<OrderMain> orderMain = orderService.findById(orderId);
        if (orderMain.isPresent() && !authentication.getName().equals(orderMain.get().getBuyerName()) && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.cancel(orderId));
    }

    @PatchMapping("/order/finish/{id}")
    public ResponseEntity<OrderMain> finish(@PathVariable("id") Long orderId, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(orderService.finish(orderId));
    }

    @PostMapping("/order/finishp/{id}")
    public ResponseEntity<OrderMain> finishPayPal(@PathVariable("id") Long orderId, Authentication authentication) {
        return ResponseEntity.ok(orderService.paypal(orderId));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long orderId, Authentication authentication) {
        boolean isCustomer = authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
        Optional<OrderMain> orderMain = orderService.findById(orderId);
        if (orderMain.isPresent()) {
            if (isCustomer && !authentication.getName().equals(orderMain.get().getBuyerName())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(orderMain.get());
        }
        return ResponseEntity.ok("FAILED");
    }

    @GetMapping("/month")
    public ResponseEntity<?> totalMonth(@RequestBody HashMap<String, Integer> month) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
