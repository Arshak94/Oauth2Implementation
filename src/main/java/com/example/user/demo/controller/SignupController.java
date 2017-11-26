package com.example.user.demo.controller;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.exception.InvalidRequestException;
import com.example.user.demo.model.User;
import com.example.user.demo.projection.PublicUserProjection;
import com.example.user.demo.service.UserService;
import com.example.user.demo.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static sun.audio.AudioDevice.device;

@Slf4j
@RestController
@RequestMapping("/")
public class SignupController {

    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Autowired
    public SignupController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder=passwordEncoder;
    }

    @PostMapping(value = "sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserPayload userPayload, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()){
            throw new InvalidRequestException("Validation issue", bindingResult);
        }
        userPayload.setPassword(passwordEncoder.encode((CharSequence) userPayload.getPassword()));
        return userService.create(userPayload);
    }

}

