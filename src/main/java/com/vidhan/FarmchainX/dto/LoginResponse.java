package com.vidhan.FarmchainX.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String message;
}
