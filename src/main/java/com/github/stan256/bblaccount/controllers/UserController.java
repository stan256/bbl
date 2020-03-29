package com.github.stan256.bblaccount.controllers;

import com.github.stan256.bblaccount.event.OnUserAccountChangeEvent;
import com.github.stan256.bblaccount.model.CustomUserDetails;
import com.github.stan256.bblaccount.model.payload.ApiResponse;
import com.github.stan256.bblaccount.model.payload.LogoutRequest;
import com.github.stan256.bblaccount.model.payload.UpdatePasswordRequest;
import com.github.stan256.bblaccount.service.AuthService;
import com.github.stan256.bblaccount.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Transactional
@RequestMapping("/api/user")
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserController(AuthService authService, UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.authService = authService;
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // todo to check authprincipal
    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity getUserProfile(@AuthenticationPrincipal CustomUserDetails currentUser) {
        log.info(currentUser.getEmail() + " has role: " + currentUser.getRoles());
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getAllAdmins() {
        log.info("Inside secured resource with admin");
        return ResponseEntity.ok("Hello. This is about admins");
    }

    @PostMapping("/password/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity updateUserPassword(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                             @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return authService.updatePassword(customUserDetails, updatePasswordRequest)
                .map(updatedUser -> {
                    OnUserAccountChangeEvent onUserPasswordChangeEvent = new OnUserAccountChangeEvent(updatedUser, "Update Password", "Change successful");
                    applicationEventPublisher.publishEvent(onUserPasswordChangeEvent);
                    return ResponseEntity.ok(new ApiResponse("Password changed successfully", true));
                })
                .orElseThrow(() -> new RuntimeException("No such user present. " + customUserDetails));
    }

    @PostMapping("/logout")
    public ResponseEntity logoutUser(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                     @Valid @RequestBody LogoutRequest logoutRequest) {
        userService.logoutUser(customUserDetails, logoutRequest);
        return ResponseEntity.ok(new ApiResponse("Log out successful", true));
    }
}
