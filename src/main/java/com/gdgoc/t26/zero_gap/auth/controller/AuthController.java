package com.gdgoc.t26.zero_gap.auth.controller;

import com.gdgoc.t26.zero_gap.auth.dto.LoginRequest;
import com.gdgoc.t26.zero_gap.auth.dto.LoginResponse;
import com.gdgoc.t26.zero_gap.auth.service.AuthService;
import com.gdgoc.t26.zero_gap.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public UserResponse getMyInfo(@AuthenticationPrincipal String email) {
        return UserResponse.from(authService.getInfo(email));
    }
}
