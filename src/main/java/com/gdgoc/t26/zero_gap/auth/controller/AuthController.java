package com.gdgoc.t26.zero_gap.auth.controller;

import com.gdgoc.t26.zero_gap.auth.dto.LoginRequest;
import com.gdgoc.t26.zero_gap.auth.dto.LoginResponse;
import com.gdgoc.t26.zero_gap.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}
