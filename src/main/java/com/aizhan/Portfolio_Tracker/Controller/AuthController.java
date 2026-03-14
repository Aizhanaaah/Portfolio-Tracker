package com.aizhan.Portfolio_Tracker.Controller;


import com.aizhan.Portfolio_Tracker.dto.AuthenticationResponse;
import com.aizhan.Portfolio_Tracker.dto.LoginRequest;
import com.aizhan.Portfolio_Tracker.dto.RegisterRequest;
import com.aizhan.Portfolio_Tracker.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        System.out.println("LOGIN REQUEST RECEIVED: " + request);
        return ResponseEntity.ok(authService.login(request));
    }
    @GetMapping("/test")
    public String test() {
        return "You are authenticated!";
    }
}
