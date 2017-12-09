package com.example.user.demo.service;

import com.example.user.demo.model.Authority;
import com.example.user.demo.model.User;
import com.example.user.demo.repository.AuthorityRepository;
import com.example.user.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date 12/9/17
 * Developer: Arshak Tovmasyan
 */
@Service
@Slf4j
public class FaceBookServiceImpl implements FaceBookService {

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

    @Autowired
    public FaceBookServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository){
        this.userRepository=userRepository;
        this.authorityRepository=authorityRepository;
    }
    @Override
    public User create(org.springframework.social.facebook.api.User user) {
        List<Authority> list = new ArrayList<>();
        Authority authority = new Authority();
        String authorityName = "ROLE_USER";
        authority.setName(authorityName);
        list.add(authority);
        if (userRepository.findByEmail(user.getEmail())!=null){
            throw new IllegalStateException("user with this email are already exist"+user.getEmail());
        }
        User myUser = new User();
        myUser.setFirstName(user.getFirstName());
        myUser.setLastName(user.getLastName());
        myUser.setEmail(user.getEmail());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            myUser.setDateOfBirth(formatter.parse(user.getBirthday()));
        } catch (ParseException e) {
            log.info(e.getMessage());
        }
        myUser.setPassword(user.getFirstName()+user.getLastName());
        myUser.setLastPasswordResetDate(new Date());
        myUser.setEnabled(true);
        authority.setUser(myUser);
        myUser.setAuthorities(list);
        userRepository.save(myUser);
        //authorityRepository.save(authority);
        return myUser;
    }
}
