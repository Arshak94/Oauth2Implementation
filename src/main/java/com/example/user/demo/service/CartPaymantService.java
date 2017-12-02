package com.example.user.demo.service;

import com.example.user.demo.binding.CartPayload;
import org.springframework.stereotype.Service;

@Service
public class CartPaymantService {

    public String  process(CartPayload cartPayload){
        double balance = cartPayload.getBalance();
        balance=balance-100.0;

        return "your purchase was made Thank you";
    }
}
