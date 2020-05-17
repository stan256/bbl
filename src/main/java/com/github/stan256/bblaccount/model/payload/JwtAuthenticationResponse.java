package com.github.stan256.bblaccount.model.payload;


import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String accessToken;
    private String refreshToken;

    public JwtAuthenticationResponse(Long id, String email, String firstName, String lastName, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accessToken = "Bearer " + accessToken;
        this.refreshToken = refreshToken;
    }
}
