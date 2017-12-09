package com.example.user.demo.binding;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class JwtAuthenticationPayload implements Serializable {
    private static final long serialVersionUID = -8445943548965154778L;

    private String email;
    private String password;

    public JwtAuthenticationPayload() {
        super();
    }

    public JwtAuthenticationPayload(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
