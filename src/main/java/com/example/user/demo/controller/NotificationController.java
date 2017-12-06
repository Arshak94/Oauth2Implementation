package com.example.user.demo.controller;

import com.example.user.demo.service.AndroidPushNotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping
public class NotificationController {

    String TOPIC = "JavaSampleApproach";

    private AndroidPushNotificationService androidPushNotificationsService;

    @Autowired
    public NotificationController(AndroidPushNotificationService androidPushNotificationService){
        this.androidPushNotificationsService=androidPushNotificationService;
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "JSA Notification");
        notification.put("body", "Happy Message!");

        JSONObject data = new JSONObject();
        data.put("Key-1", "JSA Data 1");
        data.put("Key-2", "JSA Data 2");

        body.put("notification", notification);
        body.put("data", data);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }

   /* @RequestMapping(value = "/api/restCall", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public DeferredResult<Object> restCall(@RequestBody Parameters requestBody) throws Exception {

// idToken comes from the client app (shown above)
        String idToken = requestBody.getToken();
        final DeferredResult<Object> promise = new DeferredResult<Object>();

        Task<FirebaseToken> task = FirebaseAuth.getInstance().verifyIdToken(idToken)
                .addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
                    @Override
                    public void onSuccess(FirebaseToken decodedToken) {
                        String uid = decodedToken.getUid();
                        // process the code here
                        // once it is done
                        promise.setResult(object);

                    }
                });
        return promise;

    }*/
}
