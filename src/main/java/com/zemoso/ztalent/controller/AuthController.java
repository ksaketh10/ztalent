package com.zemoso.ztalent.controller;

import com.zemoso.ztalent.db.UserRepository;
import com.zemoso.ztalent.exceptions.custom.BadRequestException;
import com.zemoso.ztalent.models.User;
import com.zemoso.ztalent.payload.LoginResponse;
import com.zemoso.ztalent.payload.LoginRequest;
import com.zemoso.ztalent.payload.RefreshTokenResponse;
import com.zemoso.ztalent.payload.SignUpRequest;
import com.zemoso.ztalent.security.CustomUserDetailsService;
import com.zemoso.ztalent.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.getAccessToken(authentication);
        String refreshToken = tokenProvider.getRefreshToken(authentication);
        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken() {
        String token = tokenProvider.getRefreshToken(SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(new RefreshTokenResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok()
                .body("User registered successfully@");
    }
}
