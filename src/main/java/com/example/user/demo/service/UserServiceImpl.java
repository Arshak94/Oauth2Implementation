package com.example.user.demo.service;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.model.Authority;
import com.example.user.demo.model.AuthorityName;
import com.example.user.demo.model.User;
import com.example.user.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public User create(UserPayload userPayload) {
        List<Authority> list = new ArrayList<>();
        Authority authority = new Authority();
        AuthorityName authorityName = AuthorityName.ROLE_ADMIN;
        authority.setName(authorityName);
        if (userRepository.findByUserName(userPayload.getUserName())!= null){
            throw new IllegalStateException("User with this email already exists");
        }
        User user = new User();
        user.setFirstName(userPayload.getFirstName());
        user.setLastName(userPayload.getLastName());
        user.setUserName(userPayload.getUserName());
        user.setEmail(userPayload.getEmail());
        user.setPassword(userPayload.getPassword());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        user.setAuthorities(list);
        return userRepository.save(user);
    }

    @Override
    public User getOne(Integer id) {
        User user = userRepository.findOne(id);
        if (user == null){
            throw new NoSuchElementException("User by id" + id + "not found");
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = (List)userRepository.findAll();
        if (users.isEmpty()){
            throw new NoSuchElementException("No users");
        }
        return users;
    }

    @Override
    public User update(Integer id, UserPayload userPayload) {
       User user1 = userRepository.findOne(id);
       if (user1==null){
           throw new NoSuchElementException("User by id" + id + "not found");
       }
       user1.setFirstName(userPayload.getFirstName());
       user1.setLastName(userPayload.getLastName());
       user1.setUserName(userPayload.getUserName());
       user1.setEmail(userPayload.getEmail());
       user1.setPassword(userPayload.getPassword());
        return userRepository.save(user1);
    }

    @Override
    public void delete(Integer id) {
        User user = userRepository.findOne(id);
        if (user == null){
            throw new NoSuchElementException("User by id" + id + "not found");
        }
        userRepository.delete(user);
    }
}
