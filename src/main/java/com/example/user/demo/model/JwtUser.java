package com.example.user.demo.model;

import com.example.user.demo.service.JwtUserFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
public class JwtUser implements UserDetails {
    private Long id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private Date dateOfBirth;
    //private BufferedImage img;
    private String profession;
    private Boolean gender;
    private Collection<GrantedAuthority> authorities;
    private boolean enabled;
    private Date lastPasswordResetDate;

    public JwtUser(
            Long id,
            String firstname,
            String lastname,
            String email,
            String password,
            Date dateOfBirth,
            String profession,
            Boolean gender,
            //BufferedImage img,
            Collection<GrantedAuthority> authorities,
            boolean enabled,
            Date lastPasswordResetDate) {
        this.id = id;

        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.dateOfBirth=dateOfBirth;
        this.profession=profession;
        this.gender=gender;
        //this.img=img;
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
        return email;
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

    public boolean getEnabled(){return enabled;}

    public Boolean getMale(){return gender;}

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public Date getDateOfBirth() {return dateOfBirth;}

    public String getProfession() {return profession;}

    /*public BufferedImage getImg() {
        return img;
    }*/

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
