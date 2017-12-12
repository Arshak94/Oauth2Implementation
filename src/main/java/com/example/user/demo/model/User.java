package com.example.user.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "confirm")
    private UUID uuid;
    @NotBlank
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @NotBlank
    @NotNull
    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @NotNull
    @NotBlank
    @Column(name = "user_password")
    private String password;

    @Column(name = "profession")
    private String profession;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss Z")
    private Date createdAt = new Date();

    @Column(name = "last_password_reset_date")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authority> authorities;
}
