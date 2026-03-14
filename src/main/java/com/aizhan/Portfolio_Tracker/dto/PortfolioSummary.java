package com.aizhan.Portfolio_Tracker.dto;

import java.math.BigDecimal;

public record PortfolioSummary(
        BigDecimal totalInvested,
        BigDecimal totalCurrentValue,
        BigDecimal totalUnrealizedPnl,
        BigDecimal totalReturnPercentage,
        int positionCount
) {}
