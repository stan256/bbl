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
        Boolean isNewUserAsAdmin = registerRequest.getRegisterAsAdmin();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());
        newUser.setRoles(getRolesForNewUser(isNewUserAsAdmin));
        newUser.setActive(true);
        newUser.setEmailVerified(false);
        return newUser;
    }

    private Set<Role> getRolesForNewUser(Boolean isToBeMadeAdmin) {
        Set<Role> newUserRoles = new HashSet<>(roleService.findAll());
        if (!isToBeMadeAdmin) {
            newUserRoles.removeIf(Role::isAdminRole);
        }
        log.info("Setting user roles: " + newUserRoles);
        return newUserRoles;
    }

    public void logoutUser(@AuthenticationPrincipal CustomUserDetails currentUser, LogoutRequest logOutRequest) {
        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        UserDevice userDevice = userDeviceService.findByUserId(currentUser.getId())
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new RuntimeException("Invalid device Id supplied. No matching device found for the given user. Device id: " + logOutRequest.getDeviceInfo().getDeviceId()));

        log.info("Removing refresh token associated with device [" + userDevice + "]");
        refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
    }
}
