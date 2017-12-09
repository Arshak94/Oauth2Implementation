package com.example.user.demo.controller;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.exception.InvalidRequestException;
import com.example.user.demo.model.JwtUser;
import com.example.user.demo.service.JwtUserFactory;
import com.example.user.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;


@Slf4j
@RestController
@RequestMapping("/")
public class SignupController {


    private UserService userService;

    @Autowired
    public SignupController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtUser create(@Valid @RequestBody UserPayload userPayload, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()){
            throw new InvalidRequestException("Validation issue", bindingResult);
        }
        if (userPayload.getPassword()==(userPayload.getRepeatPassword())){
            throw new InvalidRequestException("passwords are not the match");
        }
        JwtUser jwtUser = JwtUserFactory.create(userService.create(userPayload));
        return jwtUser;
    }

}

