package com.ntq.projectmanagement.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginViewModel {
    private String username;
    private String password;
}
