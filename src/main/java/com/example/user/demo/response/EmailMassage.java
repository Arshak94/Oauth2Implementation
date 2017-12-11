package com.example.user.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date 12/11/17
 * Developer: Arshak Tovmasyan
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMassage {
    private String massage;
}
