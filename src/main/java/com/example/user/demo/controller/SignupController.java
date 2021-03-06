package com.example.user.demo.controller;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.exception.InvalidRequestException;
import com.example.user.demo.model.JwtUser;
import com.example.user.demo.model.User;
import com.example.user.demo.repository.UserRepository;
import com.example.user.demo.response.EmailMassage;
import com.example.user.demo.service.EmailSevice;
import com.example.user.demo.service.JwtUserFactory;
import com.example.user.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URL;
import java.util.UUID;

import static com.google.api.AnnotationsProto.http;


@Slf4j
@RestController
@RequestMapping("/")
public class SignupController {


    private UserService userService;
    private EmailSevice emailSevice;

    @Autowired
    private UserRepository userRepository;

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
        UUID uuid = UUID.randomUUID();
        User user = userService.create(userPayload,uuid);

        String appUrl = request.getScheme() + "://" + request.getServerName()+":8082";
        SimpleMailMessage registrationEmail=new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl +"/"+ user.getEmail()+"/");
        System.out.println(registrationEmail.getText());
        registrationEmail.setFrom("arshak94@list.ru");
        emailSevice.sendEmail(registrationEmail);
        EmailMassage emailMassage = new EmailMassage("To confirm e-mail address, please check your email and confirm");
       // JwtUser jwtUser = JwtUserFactory.create(user);
        return emailMassage;
    }

    @GetMapping(value = "{email}/")
    public EmailMassage confirmEmail(@PathVariable String email){
        EmailMassage emailMassage = userService.get(email);
        return emailMassage;
    }

}

