package com.example.user.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "authority")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Authority {
    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority_name", length = 50)
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
