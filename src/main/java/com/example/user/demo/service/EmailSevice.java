package com.example.user.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Date 12/11/17
 * Developer: Arshak Tovmasyan
 */
@Service
public class EmailSevice {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSevice(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
