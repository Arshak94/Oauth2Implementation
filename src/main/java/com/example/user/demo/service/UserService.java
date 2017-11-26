package com.example.user.demo.service;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public User create(UserPayload userPayload);

    public User getOne(Integer id);

    public List<User> getAll();

    public User update(Integer id, UserPayload userPayload);

    public void delete(Integer id);
}
