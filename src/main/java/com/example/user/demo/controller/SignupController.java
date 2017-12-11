package com.example.user.demo.controller;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.exception.InvalidRequestException;
import com.example.user.demo.model.JwtUser;
import com.example.user.demo.model.User;
import com.example.user.demo.response.EmailMassage;
import com.example.user.demo.service.EmailSevice;
import com.example.user.demo.service.JwtUserFactory;
import com.example.user.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;


@Slf4j
@RestController
@RequestMapping("/")
public class SignupController {


    private UserService userService;
    private EmailSevice emailSevice;

    @Autowired
    public SignupController(UserService userService, EmailSevice emailSevice){

        this.userService = userService;
        this.emailSevice = emailSevice;
    }

    @PostMapping(value = "sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public EmailMassage create(@Valid @RequestBody UserPayload userPayload, BindingResult bindingResult, HttpServletRequest request) throws Exception{
        if (bindingResult.hasErrors()){
            throw new InvalidRequestException("Validation issue", bindingResult);
        }
        if (userPayload.getPassword()==(userPayload.getRepeatPassword())){
            throw new InvalidRequestException("passwords are not the match");
        }
        User user = userService.create(userPayload);
        String appUrl = request.getScheme() + "://" + request.getServerName()+"8082";
        SimpleMailMessage registrationEmail=new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/" + user.getEmail());
        registrationEmail.setFrom(user.getEmail());
        emailSevice.sendEmail(registrationEmail);
        JwtUser jwtUser = JwtUserFactory.create(user);
        EmailMassage emailMassage = new EmailMassage("To confirm e-mail address, please check your email and confirm");
        return emailMassage;
    }

    //public String confi

}

