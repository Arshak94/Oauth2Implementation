package com.example.user.demo.binding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartPayload {

    private String firstName;

    private String lastName;

    private double balance;
}
