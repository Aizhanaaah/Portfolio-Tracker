package com.aizhan.Portfolio_Tracker.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PortfolioResponse(
        Long id,
        String name,
        String description,
        Long userId,
        LocalDateTime createdAt,
        List<PositionSummary> positions,
        PortfolioSummary summary
) {}
