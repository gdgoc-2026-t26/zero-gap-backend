package com.gdgoc.t26.zero_gap.auth.service;

import com.gdgoc.t26.zero_gap.auth.dto.LoginRequest;
import com.gdgoc.t26.zero_gap.auth.dto.LoginResponse;
import com.gdgoc.t26.zero_gap.auth.JwtTokenProvider;
import com.gdgoc.t26.zero_gap.user.domain.User;
import com.gdgoc.t26.zero_gap.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtTokenProvider.createToken(user.getEmail());
        return new LoginResponse(token);
    }
}
