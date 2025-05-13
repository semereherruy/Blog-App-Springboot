package com.medco.BlogApp.DTO.Request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {
    private  String email;
    private  String password;
}
