package com.spring.baitap10.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductInOrderService productInOrderService;

    @PostMapping("")
    public ResponseEntity<?> mergeCart(@RequestBody Collection<ProductInOrder> productInOrders, Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());
        try {
            if (user.isPresent()) {
                cartService.mergeLocalCart(productInOrders, user.get());
                return ResponseEntity.ok(cartService.getCart(user.get()));
            }
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Cart Failed");
        }
        return ResponseEntity.ok("MERGE_CART_FAILED");
    }

    @GetMapping("")
    public Cart getCart(Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());
        if (user.isPresent()) return cartService.getCart(user.get());
        return null;
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
        if (user.isPresent()) {
            productInOrderService.update(itemId, quantity, user.get());
            return productInOrderService.findOne(itemId, user.get());
        }
        return null;
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId, Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());
        user.ifPresent(value -> cartService.delete(itemId, value));
    }


    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());// Email as username
        if (user.isPresent()) {
            try {
                cartService.checkout(user.get());
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponeMessage("khongdu"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ResponeMessage("false"), HttpStatus.OK);
    }

    @PostMapping("/addpro")
    public ResponseEntity<?> addpro(@RequestBody ProductInOrder productInOrder) {
        return new ResponseEntity<>(productInOrderService.save(productInOrder), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{itemId}")
    public void add1pro(@PathVariable("itemId") Long itemId) {
        productInOrderService.deleteById(itemId);
    }

    @PostMapping("/checkout1")
    public ResponseEntity<?> checkoutpay(Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());// Email as username
        if (user.isPresent()) {
            cartService.checkoutpaypal(user.get());
            return ResponseEntity.ok(new ResponeMessage("CHECKOUT_SUCCESS"));
        }
        return ResponseEntity.ok(new ResponeMessage("CHECKOUT_FAILED"));
    }
}
