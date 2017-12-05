package com.example.user.demo.binding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialPayload {

    @NotNull
    private String access_token;

    @NotNull
    private String grant_type;

}
