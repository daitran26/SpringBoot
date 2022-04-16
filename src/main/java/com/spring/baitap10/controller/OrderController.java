package com.spring.baitap10.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.baitap10.model.OrderMain;
import com.spring.baitap10.service.OrderService;
import com.spring.baitap10.service.impl.UserService;


@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	
	 @GetMapping("/order")
	    public Page<OrderMain> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
	                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
	                                     Authentication authentication) {
	        PageRequest request = PageRequest.of(page - 1, size);
	        Page<OrderMain> orderPage;
	        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
	            orderPage = orderService.findByBuyerName(authentication.getName(), request);
	            System.out.println(authentication.getPrincipal());
	        } else {
	            orderPage = orderService.findAll(request);
	        }
	        return orderPage;
	    }

	    @PatchMapping("/order/cancel/{id}")
	    public ResponseEntity<OrderMain> cancel(@PathVariable("id") Long orderId, Authentication authentication) {
	    	Optional<OrderMain> orderMain = orderService.findById(orderId);
	        if (!authentication.getName().equals(orderMain.get().getBuyerName()) && authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
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

	    @GetMapping("/order/{id}")
	    public ResponseEntity<?> show(@PathVariable("id") Long orderId, Authentication authentication) {
	        boolean isCustomer = authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"));
	        Optional<OrderMain> orderMain = orderService.findById(orderId);
	        if (isCustomer && !authentication.getName().equals(orderMain.get().getBuyerName())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	        }

//	        Collection<ProductInOrder> items = orderMain.getProducts();
	        return ResponseEntity.ok(orderMain.get());
	    }
	    @GetMapping("/month")
	    public ResponseEntity<?> totalMonth(@RequestBody HashMap<String, Integer> month) {
	        
	        return ResponseEntity.ok(orderService.totalMonth(month.get("month")));
	    }
	    @GetMapping("/all")
	    public ResponseEntity<?> getAll() {
	        return ResponseEntity.ok(orderService.findAll());
	    }
}
