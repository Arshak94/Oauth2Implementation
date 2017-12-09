package com.example.user.demo.service;

import com.example.user.demo.model.User;

/**
 * Date 12/9/17
 * Developer: Arshak Tovmasyan
 */
public interface FaceBookService {
    public User create(org.springframework.social.facebook.api.User user);
}
