package com.gdgoc.t26.zero_gap.user.controller;

import com.gdgoc.t26.zero_gap.user.dto.UserResponse;
import com.gdgoc.t26.zero_gap.user.dto.UserSignUpRequest;
import com.gdgoc.t26.zero_gap.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse signUp(@RequestBody @Valid UserSignUpRequest request) {
        return UserResponse.from(userService.signUp(request));
    }
}
