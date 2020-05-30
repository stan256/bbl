package com.github.stan256.bblaccount.controllers;

import com.github.stan256.bblaccount.event.OnGenerateResetLinkEvent;
import com.github.stan256.bblaccount.event.OnRegenerateEmailVerificationEvent;
import com.github.stan256.bblaccount.event.OnUserAccountChangeEvent;
import com.github.stan256.bblaccount.event.OnUserRegistrationCompleteEvent;
import com.github.stan256.bblaccount.model.CustomUserDetails;
import com.github.stan256.bblaccount.model.entity.EmailVerificationToken;
import com.github.stan256.bblaccount.model.entity.RefreshToken;
import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.model.payload.*;
import com.github.stan256.bblaccount.security.JwtTokenProvider;
import com.github.stan256.bblaccount.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final ApplicationEventPublisher publisher;

    @Autowired
    public AuthController(AuthService authService, ApplicationEventPublisher publisher) {
        this.authService = authService;
        this.publisher = publisher;
    }

    @GetMapping("/checkEmailInUse")
    public ResponseEntity<Boolean> checkEmailInUse(@RequestParam("email") String email) {

        Boolean emailExists = authService.emailAlreadyExists(email);
        return ResponseEntity.ok(emailExists);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new RuntimeException("Couldn't login user [" + loginRequest + "]"));

        CustomUserDetails cud = (CustomUserDetails) authentication.getPrincipal();
        log.info("Logged in User returned [API]: " + cud.getUsername());

        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String accessToken = authService.generateAccessToken(cud);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(cud.getId(), cud.getEmail(), cud.getUser().getFirstName(), cud.getUser().getLastName(), accessToken, refreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Couldn't create refresh token for: [" + loginRequest + "]"));
    }

    @PostMapping("registration")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {

        return authService.registerUser(registrationRequest)
                .map(user -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/registrationConfirmation");
                    OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent = new OnUserRegistrationCompleteEvent(user, urlBuilder);
                    publisher.publishEvent(onUserRegistrationCompleteEvent);
                    log.info("Registered User returned [API]: " + user);
                    return new ResponseEntity<>(user, HttpStatus.CREATED);
                })
                .orElseThrow(() -> new RuntimeException("Missing user object in database. Email: " + registrationRequest.getEmail()));
    }

    // todo what is this endpoint ?
    @PostMapping("/password/resetlink")
    public ResponseEntity<Boolean> resetLink(@Valid @RequestBody PasswordResetLinkRequest passwordResetLinkRequest) {

        return authService.generatePasswordResetToken(passwordResetLinkRequest)
                .map(passwordResetToken -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/password/reset");
                    OnGenerateResetLinkEvent generateResetLinkMailEvent = new OnGenerateResetLinkEvent(passwordResetToken, urlBuilder);
                    publisher.publishEvent(generateResetLinkMailEvent);
                    return ResponseEntity.ok(true);
                })
                .orElseThrow(() -> new RuntimeException("Couldn't create a valid token. Email: " + passwordResetLinkRequest.getEmail()));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {

        return authService.resetPassword(passwordResetRequest)
                .map(changedUser -> {
                    OnUserAccountChangeEvent onPasswordChangeEvent = new OnUserAccountChangeEvent(changedUser, "Reset Password", "Changed Successfully");
                    publisher.publishEvent(onPasswordChangeEvent);
                    return ResponseEntity.ok(true);
                })
                .orElseThrow(() -> new RuntimeException("Error in resetting password. Email: " + passwordResetRequest.getToken()));
    }

    @GetMapping("/registrationConfirmation")
    public ResponseEntity<Boolean> confirmRegistration(@RequestParam("token") String token) {

        return authService.confirmEmailRegistration(token)
                .map(user -> ResponseEntity.ok(true))
                .orElseThrow(() -> new RuntimeException("Failed to confirm. Please generate a new email verification request. Token: " + token));
    }

    @GetMapping("/resendRegistrationToken")
    public ResponseEntity<Boolean> resendRegistrationToken(@RequestParam("token") String existingToken) {

        EmailVerificationToken newEmailToken = authService.recreateRegistrationToken(existingToken)
                .orElseThrow(() -> new RuntimeException("User is already registered. No need to re-generate token. Token: " + existingToken));

        return Optional.of(newEmailToken.getUser())
                .map(registeredUser -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/registrationConfirmation");
                    OnRegenerateEmailVerificationEvent regenerateEmailVerificationEvent = new OnRegenerateEmailVerificationEvent(registeredUser, urlBuilder, newEmailToken);
                    publisher.publishEvent(regenerateEmailVerificationEvent);
                    return ResponseEntity.ok(true);
                })
                .orElseThrow(() -> new RuntimeException("No user associated with this request. Re-verification denied. Token: " + existingToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtRefreshResponse> refreshJwtToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        return authService.refreshJwtToken(tokenRefreshRequest)
                .map(updatedToken -> {
                    String refreshToken = tokenRefreshRequest.getRefreshToken();
                    log.info("Created new Jwt Auth token: " + updatedToken);

                    return ResponseEntity.ok(new JwtRefreshResponse(updatedToken, refreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Unexpected error during token refresh. Please logout and login again. Token: " + tokenRefreshRequest.getRefreshToken()));
    }
}
