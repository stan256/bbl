package com.github.stan256.bblaccount.service;


import com.github.stan256.bblaccount.model.*;
import com.github.stan256.bblaccount.model.entity.*;
import com.github.stan256.bblaccount.model.payload.*;
import com.github.stan256.bblaccount.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final UserDeviceService userDeviceService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public AuthService(UserService userService, JwtTokenProvider tokenProvider, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmailVerificationTokenService emailVerificationTokenService, UserDeviceService userDeviceService, PasswordResetTokenService passwordResetTokenService) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.userDeviceService = userDeviceService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    public Optional<User> registerUser(RegistrationRequest newRegistrationRequest) {
        String newRegistrationRequestEmail = newRegistrationRequest.getEmail();
        if (emailAlreadyExists(newRegistrationRequestEmail)) {
            log.error("Email already exists: " + newRegistrationRequestEmail);
            throw new RuntimeException("Email already exists: " + newRegistrationRequestEmail);
        }
        log.info("Trying to register new user [" + newRegistrationRequestEmail + "]");
        User newUser = userService.createUser(newRegistrationRequest);
        User registeredNewUser = userService.save(newUser);
        return Optional.of(registeredNewUser);
    }

    public Boolean emailAlreadyExists(String email) {
        return userService.existsByEmail(email);
    }

    public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
        return Optional.of(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())));
    }

    public Optional<User> confirmEmailRegistration(String emailToken) {
        EmailVerificationToken emailVerificationToken = emailVerificationTokenService.findByToken(emailToken)
                .orElseThrow(() -> new RuntimeException("Email token not found: " + emailToken));

        User registeredUser = emailVerificationToken.getUser();
        if (registeredUser.getEmailVerified()) {
            log.info("User [" + emailToken + "] already registered.");
            return Optional.of(registeredUser);
        }

        emailVerificationTokenService.verifyExpiration(emailVerificationToken);
        emailVerificationToken.setTokenStatus(TokenStatus.CONFIRMED);
        emailVerificationTokenService.save(emailVerificationToken);

        registeredUser.setEmailVerified(true);
        userService.save(registeredUser);
        return Optional.of(registeredUser);
    }

    public Optional<EmailVerificationToken> recreateRegistrationToken(String existingToken) {
        EmailVerificationToken emailVerificationToken = emailVerificationTokenService.findByToken(existingToken)
                .orElseThrow(() -> new RuntimeException("Email verification token does not exist. Token: " + existingToken));

        if (emailVerificationToken.getUser().getEmailVerified()) {
            return Optional.empty();
        }

        return Optional.of(emailVerificationTokenService.updateExistingTokenWithNameAndExpiry(emailVerificationToken));
    }

    /**
     * Updates the password of the current logged in user
     */
    public Optional<User> updatePassword(CustomUserDetails customUserDetails,
                                         UpdatePasswordRequest updatePasswordRequest) {
        String email = customUserDetails.getEmail();
        User currentUser = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No matching user found: " + email));

        if (!passwordEncoder.matches(currentUser.getPassword(), updatePasswordRequest.getOldPassword())) {
            log.info("Current password is invalid for [" + currentUser.getPassword() + "]");
            throw new RuntimeException("Invalid current password: " + currentUser.getEmail());
        }

        String newPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        currentUser.setPassword(newPassword);
        userService.save(currentUser);
        return Optional.of(currentUser);
    }

    public String generateToken(CustomUserDetails customUserDetails) {
        return tokenProvider.generateToken(customUserDetails);
    }

    private String generateTokenFromUserId(Long userId) {
        return tokenProvider.generateTokenFromUserId(userId);
    }

    /**
     * todo
     * Creates and persists the refresh token for the user device. If device exists
     * already, we don't care. Unused devices with expired tokens should be cleaned
     * with a cron job. The generated token would be encapsulated within the jwt.
     * Remove the existing refresh token as the old one should not remain valid.
     */
    public Optional<RefreshToken> createAndPersistRefreshTokenForDevice(Authentication authentication, LoginRequest loginRequest) {
        User currentUser = (User) authentication.getPrincipal();
        userDeviceService.findByUserId(currentUser.getId())
                .map(UserDevice::getRefreshToken)
                .map(RefreshToken::getId)
                .ifPresent(refreshTokenService::deleteById);

        UserDevice userDevice = userDeviceService.createUserDevice(loginRequest.getDeviceInfo());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken();
        userDevice.setUser(currentUser);
        userDevice.setRefreshToken(refreshToken);
        refreshToken.setUserDevice(userDevice);
        refreshToken = refreshTokenService.save(refreshToken);
        return Optional.of(refreshToken);
    }

    public Optional<String> refreshJwtToken(TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();

        return Optional.of(refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshToken -> {
                    refreshTokenService.verifyExpiration(refreshToken);
                    userDeviceService.verifyRefreshAvailability(refreshToken);
                    refreshTokenService.increaseCount(refreshToken);
                    return refreshToken;
                })
                .map(RefreshToken::getUserDevice)
                .map(UserDevice::getUser)
                .map(User::getId).map(this::generateTokenFromUserId))
                .orElseThrow(() -> new RuntimeException("Missing refresh token in database.Please login again. Token: " + requestRefreshToken));
    }

    public Optional<PasswordResetToken> generatePasswordResetToken(PasswordResetLinkRequest passwordResetLinkRequest) {
        String email = passwordResetLinkRequest.getEmail();
        return userService.findByEmail(email)
                .map(user -> {
                    PasswordResetToken passwordResetToken = passwordResetTokenService.createToken();
                    passwordResetToken.setUser(user);
                    passwordResetTokenService.save(passwordResetToken);
                    return Optional.of(passwordResetToken);
                })
                .orElseThrow(() -> new RuntimeException("No matching user found for the given request. Email: " + email));
    }

    public Optional<User> resetPassword(PasswordResetRequest passwordResetRequest) {
        String token = passwordResetRequest.getToken();
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token does not exist: " + token));

        passwordResetTokenService.verifyExpiration(passwordResetToken);
        final String encodedPassword = passwordEncoder.encode(passwordResetRequest.getPassword());

        return Optional.of(passwordResetToken)
                .map(PasswordResetToken::getUser)
                .map(user -> {
                    user.setPassword(encodedPassword);
                    userService.save(user);
                    return user;
                });
    }
}

