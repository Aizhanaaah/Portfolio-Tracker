package com.aizhan.Portfolio_Tracker.Service;

import com.aizhan.Portfolio_Tracker.dto.AuthenticationResponse;
import com.aizhan.Portfolio_Tracker.dto.LoginRequest;
import com.aizhan.Portfolio_Tracker.dto.RegisterRequest;
import com.aizhan.Portfolio_Tracker.Entity.User;
import com.aizhan.Portfolio_Tracker.Repository.UserRepository;
import com.aizhan.Portfolio_Tracker.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");

        userRepository.save(user);

        // Auto-login after register
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String jwt = jwtUtil.generateToken(auth);

        return new AuthenticationResponse(jwt, user.getUsername(), user.getRole());
    }

    public AuthenticationResponse login(LoginRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String jwt = jwtUtil.generateToken(auth);
        User customUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        return new AuthenticationResponse(jwt, customUser.getUsername(), customUser.getRole());
    }
}
