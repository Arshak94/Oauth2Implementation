package com.example.user.demo.service;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.model.Authority;
import com.example.user.demo.model.User;
import com.example.user.demo.repository.AuthorityRepository;
import com.example.user.demo.repository.UserRepository;
import com.example.user.demo.response.EmailMassage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private AuthorityRepository authorityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public EmailMassage get(String email) {
       User user = userRepository.findByEmail(email);
       user.setEnabled(true);
       userRepository.save(user);
       EmailMassage emailMassage = new EmailMassage("barev dzez");
       return emailMassage;
    }

    @Override
    public User create(UserPayload userPayload) {

        if (userRepository.findByEmail(userPayload.getEmail())!= null){
            throw new IllegalStateException("User with this email already exists");
        }
        List<Authority> list = new ArrayList<>();
        Authority authority = new Authority();
        String authorityName = "ROLE_USER";
        authority.setName(authorityName);
        list.add(authority);
        User user = new User();
        user.setFirstName(userPayload.getFirstName());
        user.setLastName(userPayload.getLastName());
        user.setEmail(userPayload.getEmail());
        user.setPassword(passwordEncoder.encode(userPayload.getPassword()));
        user.setDateOfBirth(userPayload.getDateOfBirth());
        user.setProfession(userPayload.getProfession());
        user.setEnabled(false);
        user.setLastPasswordResetDate(new Date());
        authority.setUser(user);
        userRepository.save(user);
        authorityRepository.save(authority);
        return user;
    }

    @Override
    public User getOne(Long id) {
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
    public User update(Long id, UserPayload userPayload) {
       User user1 = userRepository.findOne(id);
       if (user1==null){
           throw new NoSuchElementException("User by id" + id + "not found");
       }
       user1.setFirstName(userPayload.getFirstName());
       user1.setLastName(userPayload.getLastName());
       //user1.setUserName(userPayload.getUserName());
       user1.setEmail(userPayload.getEmail());
       user1.setPassword(passwordEncoder.encode(userPayload.getPassword()));
        return userRepository.save(user1);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findOne(id);
        if (user == null){
            throw new NoSuchElementException("User by id" + id + "not found");
        }
        userRepository.delete(user);
    }
}
