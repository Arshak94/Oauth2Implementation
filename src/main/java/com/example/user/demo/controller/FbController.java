package com.example.user.demo.controller;


import com.example.user.demo.binding.SocialPayload;
import com.example.user.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class FbController {

    @Autowired
    private FacebookConnectionFactory facebookConnectionFactory;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/my-facebook-url", method = RequestMethod.POST)
    public String fb(@RequestBody SocialPayload socialPayload) {
        try {
            if (socialPayload.getGrant_type().equals("access_token")){
                AccessGrant accessGrant = new AccessGrant(socialPayload.getAccess_token());
                Connection<Facebook> connection = facebookConnectionFactory.createConnection(accessGrant);
                UserProfile userProfile = connection.fetchUserProfile();
                //System.out.println(userProfile.getEmail());
                usersConnectionRepository.createConnectionRepository(userProfile.getEmail()).addConnection(connection);
               // JwtUser jwtUser = JwtUserFactory.create(userService.create())
            }
            } catch (RuntimeException e){
            log.info("your token is not valid please verify it");
        }
        /*else
        System.out.println(socialPayload.toString());
        ObjectMapper mapper = new ObjectMapper();
        String t = new String();
        try {
            t = mapper.readValue(token, String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/



        //System.out.println(userProfile.getFirstName() + " " + userProfile.getLastName());


        return "Done";
    }
}
