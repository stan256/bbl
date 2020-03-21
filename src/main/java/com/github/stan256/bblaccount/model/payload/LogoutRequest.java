package com.github.stan256.bblaccount.model.payload;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class LogoutRequest {
    @Valid
    @NotNull(message = "Device info cannot be null")
    private DeviceInfo deviceInfo;
}
