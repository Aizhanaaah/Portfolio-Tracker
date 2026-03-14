package com.aizhan.Portfolio_Tracker.dto;

import java.math.BigDecimal;

public record PositionSummary(
        Long positionId,
        String assetTicker,
        String assetName,
        BigDecimal quantity,
        BigDecimal averageBuyPrice,
        BigDecimal currentPrice,
        BigDecimal currentValue,
        BigDecimal unrealizedPnl,
        BigDecimal pnlPercentage
) {}
