package com.example.user.demo.controller;


import com.example.user.demo.binding.SocialPayload;
import com.example.user.demo.model.JwtUser;
import com.example.user.demo.model.User;
import com.example.user.demo.repository.UserRepository;
import com.example.user.demo.service.FaceBookService;
import com.example.user.demo.service.JwtUserFactory;
import com.example.user.demo.service.UserService;
import com.example.user.demo.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.EducationExperience;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class FbController {

    @Autowired
    private FaceBookService faceBookService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/my-facebook-url", method = RequestMethod.POST)
    public Map<String, String> fb(@RequestBody SocialPayload socialPayload, Device device) {
        JwtUser jwtUser = new JwtUser();
        Map<String, String> tokenMap = new HashMap<>();
        try {
            if (socialPayload.getGrant_type().equals("access_token")){
                FacebookTemplate facebookTemplate = new FacebookTemplate(socialPayload.getAccess_token());
                org.springframework.social.facebook.api.User user1 = facebookTemplate.userOperations().getUserProfile();
                jwtUser  = JwtUserFactory.create(faceBookService.create(user1));
                final String accesstoken = jwtTokenUtil.generateToken(jwtUser, device);
                final String refreshToken = jwtTokenUtil.generateRefreshToken(jwtUser, device);
                tokenMap.put("access_token", accesstoken);
                tokenMap.put("refresh_token", refreshToken);

                }
            } catch (RuntimeException e){
            log.info("your token is not valid please verify it");
        }



        return tokenMap;
    }
}
