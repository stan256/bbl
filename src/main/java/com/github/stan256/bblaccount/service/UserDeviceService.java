package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.model.entity.RefreshToken;
import com.github.stan256.bblaccount.model.entity.UserDevice;
import com.github.stan256.bblaccount.model.payload.DeviceInfo;
import com.github.stan256.bblaccount.repo.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    @Autowired
    public UserDeviceService(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    public Optional<UserDevice> findByUserId(Long userId) {
        return userDeviceRepository.findByUserId(userId);
    }

    public Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken) {
        return userDeviceRepository.findByRefreshToken(refreshToken);
    }

    public UserDevice createUserDevice(DeviceInfo deviceInfo) {
        UserDevice userDevice = new UserDevice();
        userDevice.setDeviceId(deviceInfo.getDeviceId());
        userDevice.setDeviceType(deviceInfo.getDeviceType());
        userDevice.setNotificationToken(deviceInfo.getNotificationToken());
        userDevice.setRefreshActive(true);
        return userDevice;
    }

    void verifyRefreshAvailability(RefreshToken refreshToken) {
        UserDevice userDevice = findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("No device found for the matching token. Please login again. Token: " + refreshToken.getToken()));

        if (!userDevice.getRefreshActive()) {
            throw new RuntimeException("Refresh blocked for the device. Please login through a different device. Token: " + refreshToken.getToken());
        }
    }
}
