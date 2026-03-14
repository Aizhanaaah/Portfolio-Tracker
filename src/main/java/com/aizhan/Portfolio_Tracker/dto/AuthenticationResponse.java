package com.aizhan.Portfolio_Tracker.dto;

public record AuthenticationResponse(
        String token,
        String username,
        String role
) {}
