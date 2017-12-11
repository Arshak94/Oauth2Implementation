package com.example.user.demo.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.social.google.connect.GoogleServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * Date 12/9/17
 * Developer: Arshak Tovmasyan
 */
@RestController
public class GoogleController {


    @Value("${spring.social.google.appId}")
    private String googleappId;

    @Value("${spring.social.google.appSecret}")
    private String googleappSecret;

    @PostMapping("/my-google-url")
    public void google(@RequestBody String token){
        GoogleServiceProvider googleServiceProvider = new GoogleServiceProvider(googleappId, googleappSecret);
        googleServiceProvider.getApi(token)
                .plusOperations().getGoogleProfile().getEmailAddresses();
        /*HttpTransport netTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleTokenResponse token = null;
        try {
            token = new GoogleAuthorizationCodeTokenRequest(netTransport,
                    jsonFactory, googleappId, googleappSecret, "4/yom2uE-KRDJrimsD-ud2_g58zTQ2wVknWuCEyg9JmQE",
                    "https://www.googleapis.com/oauth2/v2/userinfo").execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Valid access token " + token.getAccessToken());*/
        /*GoogleCredential cd = new GoogleCredential().setAccessToken(token
                .getAccessToken());
        googleServiceProvider.getApi()
        System.out.println(googleServiceProvider.getApi().plusOperations().getGoogleProfile());*/
    }


}
