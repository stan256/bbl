package com.github.stan256.bblaccount.controllers;

import com.github.stan256.bblaccount.event.OnGenerateResetLinkEvent;
import com.github.stan256.bblaccount.event.OnRegenerateEmailVerificationEvent;
import com.github.stan256.bblaccount.event.OnUserAccountChangeEvent;
import com.github.stan256.bblaccount.event.OnUserRegistrationCompleteEvent;
import com.github.stan256.bblaccount.model.CustomUserDetails;
import com.github.stan256.bblaccount.model.entity.EmailVerificationToken;
import com.github.stan256.bblaccount.model.entity.RefreshToken;
import com.github.stan256.bblaccount.model.payload.*;
import com.github.stan256.bblaccount.security.JwtTokenProvider;
import com.github.stan256.bblaccount.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AuthController(AuthService authService, JwtTokenProvider tokenProvider, ApplicationEventPublisher applicationEventPublisher) {
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping("/checkEmailInUse")
    public ResponseEntity checkEmailInUse(@RequestParam("email") String email) {

        Boolean emailExists = authService.emailAlreadyExists(email);
        return ResponseEntity.ok(new ApiResponse(emailExists.toString(), true));
    }

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new RuntimeException("Couldn't login user [" + loginRequest + "]"));

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("Logged in User returned [API]: " + customUserDetails.getUsername());

        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String jwtToken = authService.generateToken(customUserDetails);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new RuntimeException("Couldn't create refresh token for: [" + loginRequest + "]"));
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {

        return authService.registerUser(registrationRequest)
                .map(user -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/registrationConfirmation");
                    OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent = new OnUserRegistrationCompleteEvent(user, urlBuilder);
                    applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
                    log.info("Registered User returned [API[: " + user);
                    return ResponseEntity.ok(new ApiResponse("User registered successfully. Check your email for verification", true));
                })
                .orElseThrow(() -> new RuntimeException("Missing user object in database. Email: " + registrationRequest.getEmail()));
    }

    @PostMapping("/password/resetlink")
    public ResponseEntity resetLink(@Valid @RequestBody PasswordResetLinkRequest passwordResetLinkRequest) {
        return authService.generatePasswordResetToken(passwordResetLinkRequest)
                .map(passwordResetToken -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/password/reset");
                    OnGenerateResetLinkEvent generateResetLinkMailEvent = new OnGenerateResetLinkEvent(passwordResetToken, urlBuilder);
                    applicationEventPublisher.publishEvent(generateResetLinkMailEvent);
                    return ResponseEntity.ok(new ApiResponse("Password reset link sent successfully", true));
                })
                .orElseThrow(() -> new RuntimeException("Couldn't create a valid token. Email: " + passwordResetLinkRequest.getEmail()));
    }

    @PostMapping("/password/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        return authService.resetPassword(passwordResetRequest)
                .map(changedUser -> {
                    OnUserAccountChangeEvent onPasswordChangeEvent = new OnUserAccountChangeEvent(changedUser, "Reset Password", "Changed Successfully");
                    applicationEventPublisher.publishEvent(onPasswordChangeEvent);
                    return ResponseEntity.ok(new ApiResponse("Password changed successfully", true));
                })
                .orElseThrow(() -> new RuntimeException("Error in resetting password. Email: " + passwordResetRequest.getToken()));
    }

    @GetMapping("/registrationConfirmation")
    public ResponseEntity confirmRegistration(@RequestParam("token") String token) {
        return authService.confirmEmailRegistration(token)
                .map(user -> ResponseEntity.ok(new ApiResponse("User verified successfully", true)))
                .orElseThrow(() -> new RuntimeException("Failed to confirm. Please generate a new email verification request. Token: " + token));
    }

    @GetMapping("/resendRegistrationToken")
    public ResponseEntity resendRegistrationToken(@RequestParam("token") String existingToken) {
        EmailVerificationToken newEmailToken = authService.recreateRegistrationToken(existingToken)
                .orElseThrow(() -> new RuntimeException("User is already registered. No need to re-generate token. Token: " + existingToken));

        return Optional.of(newEmailToken.getUser())
                .map(registeredUser -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/registrationConfirmation");
                    OnRegenerateEmailVerificationEvent regenerateEmailVerificationEvent = new OnRegenerateEmailVerificationEvent(registeredUser, urlBuilder, newEmailToken);
                    applicationEventPublisher.publishEvent(regenerateEmailVerificationEvent);
                    return ResponseEntity.ok(new ApiResponse("Email verification resent successfully", true));
                })
                .orElseThrow(() -> new RuntimeException("No user associated with this request. Re-verification denied. Token: " + existingToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity refreshJwtToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return authService.refreshJwtToken(tokenRefreshRequest)
                .map(updatedToken -> {
                    String refreshToken = tokenRefreshRequest.getRefreshToken();
                    log.info("Created new Jwt Auth token: " + updatedToken);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(updatedToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error during token refresh. Please logout and login again. Token: " + tokenRefreshRequest.getRefreshToken()));
    }
}
