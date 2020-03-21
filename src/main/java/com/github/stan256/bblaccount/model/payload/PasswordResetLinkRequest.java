package com.github.stan256.bblaccount.model.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetLinkRequest {
    @NotBlank
    private String email;
}

