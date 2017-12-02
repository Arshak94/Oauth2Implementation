package com.example.user.demo.controller;

import com.example.user.demo.binding.CartPayload;
import com.example.user.demo.exception.InvalidRequestException;
import com.example.user.demo.response.CartResponce;
import com.example.user.demo.service.CartPaymantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class PaymantController {

    private CartPaymantService cartPaymantService;

    @Autowired
    public PaymantController(CartPaymantService cartPaymantService){
      this.cartPaymantService = cartPaymantService;
    }

    @PutMapping("/buy")
    public CartResponce buy(@Valid @RequestBody CartPayload cartPayload, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new InvalidRequestException("validation issue", bindingResult);
        }
        CartResponce cartResponce = new CartResponce();
        cartResponce.setMassage(cartPaymantService.process(cartPayload));
        return cartResponce;
    }
}
