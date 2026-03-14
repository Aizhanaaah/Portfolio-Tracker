package com.aizhan.Portfolio_Tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PortfolioCreateRequest(
        @NotBlank @Size(min = 3, max = 50) String name,
        @Size(max = 500) String description
) {}
