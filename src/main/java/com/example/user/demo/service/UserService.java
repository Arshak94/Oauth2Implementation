package com.example.user.demo.service;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.model.User;
import com.example.user.demo.response.EmailMassage;
import org.springframework.social.connect.UserProfile;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public EmailMassage get(String email);

    public User create(UserPayload userPayload, UUID uuid);

    public User getOne(Long id);

    public List<User> getAll();

    public User update(Long id, UserPayload userPayload);

    public void delete(Long id);
}
