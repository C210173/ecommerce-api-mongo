package com.sky.ecommerce.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
}
