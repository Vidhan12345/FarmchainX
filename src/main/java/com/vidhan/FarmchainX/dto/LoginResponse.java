package com.vidhan.FarmchainX.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken; // Frontend expects "accessToken" not "token"
    private UserInfo user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfo {
        private String id;
        private String name;
        private String email;
        private String role; // Single role, not list
        private String profileImage;
        private String phone;
        private String address;
        private String company;
        private Boolean verified;
    }
}
