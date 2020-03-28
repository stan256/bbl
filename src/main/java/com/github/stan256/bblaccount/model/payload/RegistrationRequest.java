package com.github.stan256.bblaccount.model.payload;

import com.github.stan256.bblaccount.annotation.NullOrNotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NullOrNotBlank
    private String email;

    @NotNull
    private String password;
}
