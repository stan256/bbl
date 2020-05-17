package com.github.stan256.bblaccount.model.payload;


import lombok.Data;

@Data
public class JwtRefreshResponse {
    private String accessToken;
    private String refreshToken;

    public JwtRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = "Bearer " + accessToken;
        this.refreshToken = refreshToken;
    }
}
