package com.aizhan.Portfolio_Tracker.dto;

public record RegisterRequest(
        String username,
        String email,
        String password
) {}
