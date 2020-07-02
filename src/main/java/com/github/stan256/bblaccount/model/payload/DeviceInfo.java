package com.github.stan256.bblaccount.model.payload;

import com.github.stan256.bblaccount.annotation.NullOrNotBlank;
import com.github.stan256.bblaccount.model.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {
    @NotBlank
    private String deviceId;

    @NotNull
    private DeviceType deviceType;
}
