package com.spring.baitap10.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.baitap10.DTO.Request.ItemForm;
import com.spring.baitap10.DTO.Respone.ResponeMessage;
import com.spring.baitap10.model.Cart;
import com.spring.baitap10.model.Product;
import com.spring.baitap10.model.ProductInOrder;
import com.spring.baitap10.model.User;
import com.spring.baitap10.service.CartService;
import com.spring.baitap10.service.ProductInOrderService;
import com.spring.baitap10.service.impl.ProductService;
import com.spring.baitap10.service.impl.UserService;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	UserService userService;
	@Autowired
	CartService cartService;
	@Autowired
	ProductService productService;
	@Autowired
	ProductInOrderService productInOrderService;
	
	@PostMapping("")
    public ResponseEntity<Cart> mergeCart(@RequestBody Collection<ProductInOrder> productInOrders, Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());
        try {
            cartService.mergeLocalCart(productInOrders, user.get());
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Cart Failed");
        }
        return ResponseEntity.ok(cartService.getCart(user.get()));
    }
	@GetMapping("")
    public Cart getCart(Principal principal) {
		Optional<User> user = userService.findByUsername(principal.getName());
        return cartService.getCart(user.get());
    }
	@PostMapping("/add")
    public boolean addToCart(@RequestBody ItemForm form, Principal principal) {
        Product productInfo = productService.getProductById(form.getProductId());
        try {
            mergeCart(Collections.singleton(new ProductInOrder(productInfo, form.getQuantity())), principal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
	@PutMapping("/{itemId}")
    public ProductInOrder modifyItem(@PathVariable("itemId") Long itemId, @RequestBody Integer quantity, Principal principal) {
		Optional<User> user = userService.findByUsername(principal.getName());
         productInOrderService.update(itemId, quantity, user.get());
        return productInOrderService.findOne(itemId, user.get());
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId, Principal principal) {
    	Optional<User> user = userService.findByUsername(principal.getName());
         cartService.delete(itemId, user.get());
         // flush memory into DB
    }


    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(Principal principal) {
    	Optional<User> user = userService.findByUsername(principal.getName());// Email as username
    	if(user!=null) {
    		cartService.checkout(user.get());
    		return new ResponseEntity<>(new ResponeMessage("success"),HttpStatus.OK);
    	}
    	return new ResponseEntity<>(new ResponeMessage("false"),HttpStatus.OK);
    }
    
    @PostMapping("/addpro")
    public ResponseEntity<?> addpro(@RequestBody ProductInOrder productInOrder) {
    	return new ResponseEntity<>(productInOrderService.save(productInOrder),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{itemId}")
    public void add1pro(@PathVariable("itemId") Long itemId) {
    	productInOrderService.deleteById(itemId);
    }
}
