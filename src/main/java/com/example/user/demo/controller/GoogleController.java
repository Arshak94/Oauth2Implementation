package com.example.user.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.connect.GoogleServiceProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
