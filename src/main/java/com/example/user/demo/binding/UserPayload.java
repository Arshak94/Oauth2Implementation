package com.example.user.demo.binding;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload {


    private String firstName;

    private String lastName;


    private String email;


    private String password;


    private String repeatPassword;

    private Date dateOfBirth;


    private String profession;


}
