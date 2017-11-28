package com.example.user.demo.service;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.model.User;

import java.util.List;

public interface UserService {

    public User create(UserPayload userPayload);

    public User getOne(Long id);

    public List<User> getAll();

    public User update(Long id, UserPayload userPayload);

    public void delete(Long id);
}
