package org.example.expert.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.auth.dto.response.SigninResponse;
import org.example.expert.domain.auth.dto.response.SignupResponse;
import org.example.expert.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequest signupRequest) {
        SignupResponse signup = authService.signup(signupRequest);
        return ResponseEntity.ok().header("Authorization", signup.getBearerToken()).build();
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<Void> signin(@RequestBody SigninRequest signinRequest) {
        SigninResponse signin = authService.signin(signinRequest);
        return ResponseEntity.ok().header("Authorization", signin.getBearerToken()).build();
    }
}
