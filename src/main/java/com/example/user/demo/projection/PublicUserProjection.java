package com.example.user.demo.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;


public interface PublicUserProjection {

    Integer getId();

    String getUserName();

    String getFirstName();

    String getLastName();

    String getEmail();

    Date getCreateAt();

    String getPassword();

    @JsonIgnore
    Class<?> getDecoratedClass();

}
