package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.model.CustomUserDetails;
import com.github.stan256.bblaccount.model.entity.Role;
import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.model.entity.UserDevice;
import com.github.stan256.bblaccount.model.payload.LogoutRequest;
import com.github.stan256.bblaccount.model.payload.RegistrationRequest;
import com.github.stan256.bblaccount.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserDeviceService userDeviceService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       RoleService roleService,
                       UserDeviceService userDeviceService,
                       RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userDeviceService = userDeviceService;
        this.refreshTokenService = refreshTokenService;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long Id) {
        return userRepository.findById(Id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User createUser(RegistrationRequest registerRequest) {
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());
        newUser.setRoles(new HashSet<>(Arrays.asList(roleService.findUserRole().orElseThrow())));
        newUser.setActive(true);
        newUser.setEmailVerified(false);
        return newUser;
    }

    public void logoutUser(@AuthenticationPrincipal CustomUserDetails currentUser, LogoutRequest logOutRequest) {
        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        userDeviceService.findAllByUserId(currentUser.getId())
                .stream()
                .filter(device -> device.getDeviceId().equals(deviceId))
                .forEach(userDevice -> refreshTokenService.deleteById(userDevice.getRefreshToken().getId()));
    }
}
