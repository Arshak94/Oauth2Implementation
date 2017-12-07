package com.example.user.demo.model;

import com.example.user.demo.service.JwtUserFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
public class JwtUser implements UserDetails {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private Collection<GrantedAuthority> authorities;
    private boolean enabled;
    private Date lastPasswordResetDate;

    public JwtUser(
            Long id,
            String username,
            String firstname,
            String lastname,
            String email,
            String password, Collection<GrantedAuthority> authorities,
            boolean enabled,
            Date lastPasswordResetDate) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public JwtUser(){

    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public boolean getEnabled(){return enabled;}

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setAuthorities(List<Authority> authorities) {
        JwtUserFactory.mapToGrantedAuthorities(authorities);
    }
}
