package com.github.stan256.bblaccount.model.payload;


import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiryDuration;

    public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration) {
        this.accessToken = "Bearer " + accessToken;
        this.refreshToken = refreshToken;
        this.expiryDuration = expiryDuration;
    }
}
